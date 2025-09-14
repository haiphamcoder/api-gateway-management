package io.github.haiphamcoder.gateway.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.Role;
import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.User;
import io.github.haiphamcoder.gateway.layer.application.repository.RoleRepository;
import io.github.haiphamcoder.gateway.layer.application.repository.UserRepository;
import io.github.haiphamcoder.gateway.layer.application.service.AuthService;
import io.github.haiphamcoder.gateway.layer.application.service.PasswordHasher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordHasher passwordHasher;
    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeAdminUser();
    }

    private void initializeRoles() {
        authService.initializeDefaultRoles();
        log.info("Roles initialized successfully");
    }

    private void initializeAdminUser() {
        User user = userRepository.getUserByEmail("admin@example.com");
        if (user == null) {
            Role adminRole = roleRepository.getRoleByCode(Role.RoleCode.ADMIN.getCode());
            if (adminRole == null) {
                throw new IllegalStateException("Admin role not found");
            }
            User adminUser = User.builder()
                .email("admin@example.com")
                .passwordHash(passwordHasher.hashPassword("admin@123"))
                .name("Administrator")
                .locale("en")
                .roles(Set.of(adminRole))
                .build();

            User savedUser = userRepository.createUser(adminUser);
            log.info("Admin user created successfully: {}", savedUser.getEmail());
        } else {
            log.info("Admin user already exists: {}", user.getEmail());
        }
    }

}
