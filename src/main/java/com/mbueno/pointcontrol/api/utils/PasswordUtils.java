package com.mbueno.pointcontrol.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    public PasswordUtils() {
    }

    /**
     * Generate a hash to password using BCrypt
     *
     * @param senha
     * @return String
     */
    public static String gerarBCrypt(String senha) {
        if (senha == null) {
            return senha;
        }
        log.info("Gerando has com BCrypt");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(senha);
    }
}
