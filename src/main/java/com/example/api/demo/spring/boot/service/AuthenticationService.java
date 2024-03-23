package com.example.api.demo.spring.boot.service;

import com.example.api.demo.spring.boot.dto.request.AuthenticationRequest;
import com.example.api.demo.spring.boot.dto.request.IntrospectRequest;
import com.example.api.demo.spring.boot.dto.response.AuthenticationResponse;
import com.example.api.demo.spring.boot.dto.response.IntrospectResponse;
import com.example.api.demo.spring.boot.entity.User;
import com.example.api.demo.spring.boot.exception.AppException;
import com.example.api.demo.spring.boot.exception.ErrorCode;
import com.example.api.demo.spring.boot.helper.EnvHelper;
import com.example.api.demo.spring.boot.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Optional<User> userOptional = this.userRepository.findByUsername(request.getUsername());
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        User user = userOptional.get();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateTokenJWT(user.getUsername());

        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    private String generateTokenJWT(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("api-spring-dev")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli()))
                .claim("customClaim", "customClaimValue")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(EnvHelper.getEnv("JWT_SECRET_KEY").getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            System.out.print("Can not create token");
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspectToken(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(EnvHelper.getEnv("JWT_SECRET_KEY").getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiredTime.after(new Date()))
                .build();
    }
}
