package br.com.treinaweb.twprojetos.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtil {
    public static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean matches(String password, String hash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hash);
    }
}
