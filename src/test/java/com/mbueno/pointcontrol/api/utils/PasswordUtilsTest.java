package com.mbueno.pointcontrol.api.utils;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNull;

public class PasswordUtilsTest {
    private static final String SENHA = "123456";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testSenhaNula() throws Exception {
        assertNull(PasswordUtils.gerarBCrypt(null));
    }

    @Test
    public void testGerarHashSenha() {
        String hash = PasswordUtils.gerarBCrypt(SENHA);
        assertTrue(bCryptPasswordEncoder.matches(SENHA, hash));
    }
}
