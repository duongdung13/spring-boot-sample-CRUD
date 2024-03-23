package com.example.api.demo.spring.boot.helper;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvHelper {
    public static String getEnv(String var) {
        Dotenv dotenv = Dotenv.load(); // Load .env file
        String databaseUrl = dotenv.get(var);
        return databaseUrl == null ? "" : databaseUrl;
    }
}
