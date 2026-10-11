package com.example.tutoringapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PasswordHasherTest {

    @Test
    public void samePasswordAndSalt_givesSameHash() {
        String salt = PasswordHasher.generateSalt();
        assertEquals(PasswordHasher.hash("secret123", salt), PasswordHasher.hash("secret123", salt));
    }

    @Test
    public void samePasswordDifferentSalt_givesDifferentHash() {
        String hash1 = PasswordHasher.hash("secret123", PasswordHasher.generateSalt());
        String hash2 = PasswordHasher.hash("secret123", PasswordHasher.generateSalt());
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void hash_doesNotContainThePlainPassword() {
        String salt = PasswordHasher.generateSalt();
        assertFalse(PasswordHasher.hash("secret123", salt).contains("secret123"));
    }

    @Test
    public void verify_acceptsCorrectPassword() {
        String salt = PasswordHasher.generateSalt();
        String hash = PasswordHasher.hash("secret123", salt);
        assertTrue(PasswordHasher.verify("secret123", salt, hash));
    }

    @Test
    public void verify_rejectsWrongPassword() {
        String salt = PasswordHasher.generateSalt();
        String hash = PasswordHasher.hash("secret123", salt);
        assertFalse(PasswordHasher.verify("secret124", salt, hash));
    }

    @Test
    public void generatedSalts_areUniqueAndHex() {
        String a = PasswordHasher.generateSalt();
        String b = PasswordHasher.generateSalt();
        assertNotEquals(a, b);
        assertEquals(32, a.length());
        assertTrue(a.matches("[0-9a-f]+"));
    }
}
