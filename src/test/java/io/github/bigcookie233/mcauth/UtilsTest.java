package io.github.bigcookie233.mcauth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void validateMinecraftId() {
        assertTrue(Utils.validateMinecraftId("Technoblade"));
        assertTrue(Utils.validateMinecraftId("_BIG_COOKIE_"));
        assertFalse(Utils.validateMinecraftId("hi"));
        assertFalse(Utils.validateMinecraftId("&*^%@#"));
    }

    @Test
    void validateCode() {
        assertTrue(Utils.validateCode("123456"));
        assertFalse(Utils.validateCode("12345678"));
        assertFalse(Utils.validateCode("abcdef"));
        assertFalse(Utils.validateCode("&*^%@#"));
    }

    @Test
    void calculateSHA256() {
        assertEquals("APEUPGWIFO6M2VENDWAIHTWXYTWRP2B5FZGYRZ5AOZW3FFN5VZMQ====", Utils.calculateSHA256("_BIG_COOKIE_"));
    }
}