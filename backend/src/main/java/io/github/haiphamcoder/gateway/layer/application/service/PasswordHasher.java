package io.github.haiphamcoder.gateway.layer.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    /**
     * Hash a password using the password encoder
     * 
     * @param password the password to hash
     * @return the hashed password
     */
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Verify a password against a hashed password
     * 
     * @param password       the password to verify
     * @param hashedPassword the hashed password to verify against
     * @return true if the password is valid, false otherwise
     */
    public boolean verifyPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

}
