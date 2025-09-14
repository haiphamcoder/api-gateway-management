package io.github.haiphamcoder.gateway.layer.application.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.LoginResponse;
import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.Role;
import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.User;
import io.github.haiphamcoder.gateway.layer.application.repository.RoleRepository;
import io.github.haiphamcoder.gateway.layer.application.repository.UserRepository;
import io.github.haiphamcoder.gateway.layer.application.usecase.AuthenticationManagementUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements AuthenticationManagementUsecase {

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Override
    public LoginResponse login(String email, String password, boolean isRememberMe) {
        User user = userRepository.getUserByEmailWithRoles(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        if (!passwordHasher.verifyPassword(password, user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid password");
        }
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new BadCredentialsException("User is not active");
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getEmail());

        if (isRememberMe) {
            redisTemplate.opsForValue()
                    .set(REFRESH_TOKEN_PREFIX + user.getId(), refreshToken, Duration.ofDays(30));
        } else {
            redisTemplate.opsForValue()
                    .set(REFRESH_TOKEN_PREFIX + user.getId(), refreshToken, Duration.ofHours(7));
        }

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(new LoginResponse.UserInfo(user))
                .expiresIn(accessTokenExpiration / 1000)
                .build();

    }

    @Override
    public LoginResponse refreshToken(String token) {
        try {
            Long userId = jwtService.extractUserId(token);

            if (!jwtService.isRefreshToken(token)){
                throw new BadCredentialsException("Invalid token type");
            }

            String storedToken = redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + userId);
            if (storedToken == null || !storedToken.equals(token)) {
                throw new BadCredentialsException("Invalid token");
            }

            User user = userRepository.getUserByIdWithRoles(userId);
            if (user == null) {
                throw new BadCredentialsException("User not found");
            }

            if (user.getStatus() != User.UserStatus.ACTIVE) {
                throw new BadCredentialsException("User is not active");
            }

            String newAccessToken = jwtService.generateAccessToken(userId, user.getEmail());
            String newRefreshToken = jwtService.generateRefreshToken(userId, user.getEmail());

            redisTemplate.opsForValue()
                    .set(REFRESH_TOKEN_PREFIX + userId, newRefreshToken, Duration.ofHours(7));

            return LoginResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .user(new LoginResponse.UserInfo(user))
                    .expiresIn(accessTokenExpiration / 1000)
                    .build();
        } catch (Exception e) {
            log.error("Error refreshing token: {}", e.getMessage());
            throw new BadCredentialsException("Invalid token");
        }
    }

    @Override
    public void logout(Long userId) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
    }

    public void initializeDefaultRoles() {
        for (Role.RoleCode roleCode : Role.RoleCode.values()) {
            if (!roleRepository.existsByCode(roleCode.getCode())) {
                Role role = new Role(roleCode.getCode(), roleCode.getDescription());
                Role savedRole = roleRepository.createRole(role);
                log.info("Created default role: {}", savedRole.getCode());
            }
        }
    }

}
