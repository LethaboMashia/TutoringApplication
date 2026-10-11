package com.example.tutoringapplication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InputValidatorTest {

    @Test
    public void validEmail_isAccepted() {
        assertTrue(InputValidator.isValidEmail("student@eduvos.com"));
        assertTrue(InputValidator.isValidEmail("first.last+tag@mail.co.za"));
    }

    @Test
    public void validEmail_trimsWhitespace() {
        assertTrue(InputValidator.isValidEmail("  student@eduvos.com  "));
    }

    @Test
    public void invalidEmail_isRejected() {
        assertFalse(InputValidator.isValidEmail(""));
        assertFalse(InputValidator.isValidEmail("student"));
        assertFalse(InputValidator.isValidEmail("student@"));
        assertFalse(InputValidator.isValidEmail("@eduvos.com"));
        assertFalse(InputValidator.isValidEmail("student@eduvos"));
        assertFalse(InputValidator.isValidEmail("stu dent@eduvos.com"));
    }

    @Test
    public void nullEmail_isRejected() {
        assertFalse(InputValidator.isValidEmail(null));
    }

    @Test
    public void validName_needsAtLeastTwoCharacters() {
        assertTrue(InputValidator.isValidName("Li"));
        assertTrue(InputValidator.isValidName("Lethabo Mashia"));
        assertFalse(InputValidator.isValidName("A"));
        assertFalse(InputValidator.isValidName("   "));
        assertFalse(InputValidator.isValidName(null));
    }

    @Test
    public void validPassword_needsLengthLetterAndDigit() {
        assertTrue(InputValidator.isValidPassword("abcdefg1"));
        assertTrue(InputValidator.isValidPassword("Passw0rdLong"));
    }

    @Test
    public void shortPassword_isRejected() {
        assertFalse(InputValidator.isValidPassword("abc123"));
    }

    @Test
    public void passwordWithoutDigit_isRejected() {
        assertFalse(InputValidator.isValidPassword("abcdefghij"));
    }

    @Test
    public void passwordWithoutLetter_isRejected() {
        assertFalse(InputValidator.isValidPassword("1234567890"));
    }

    @Test
    public void nullPassword_isRejected() {
        assertFalse(InputValidator.isValidPassword(null));
    }

    @Test
    public void passwordsMatch_onlyWhenIdentical() {
        assertTrue(InputValidator.passwordsMatch("abc12345", "abc12345"));
        assertFalse(InputValidator.passwordsMatch("abc12345", "abc12346"));
        assertFalse(InputValidator.passwordsMatch("abc12345", null));
    }
}
