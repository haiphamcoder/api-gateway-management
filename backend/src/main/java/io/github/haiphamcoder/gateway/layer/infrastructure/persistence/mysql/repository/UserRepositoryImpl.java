package io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.repository;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.Role;
import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.User;
import io.github.haiphamcoder.gateway.layer.application.repository.UserRepository;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.entity.RoleEntity;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.entity.UserEntity;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.mapper.UserEntityMapper;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.repository.jpa.RoleEntityJpaRepository;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.repository.jpa.UserEntityJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserEntityJpaRepository userEntityJpaRepository;
    private final RoleEntityJpaRepository roleEntityJpaRepository;

    @Override
    public User getUserByEmailWithRoles(String email) {
        return userEntityJpaRepository.findByEmailWithRoles(email)
                .map(UserEntityMapper::toModel)
                .orElse(null);
    }

    @Override
    public User getUserByIdWithRoles(Long id) {
        return userEntityJpaRepository.findByIdWithRoles(id)
                .map(UserEntityMapper::toModel)
                .orElse(null);
    }

    @Override
    @Transactional
    @Modifying
    public User createUser(User user) {
        UserEntity userEntity = UserEntityMapper.toEntity(user);

        // Handle role associations properly by fetching existing role entities
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<RoleEntity> existingRoleEntities = user.getRoles().stream()
                    .map(this::getExistingRoleEntity)
                    .collect(Collectors.toSet());
            userEntity = userEntity.toBuilder()
                    .roles(existingRoleEntities)
                    .build();
        }

        return UserEntityMapper.toModel(userEntityJpaRepository.save(userEntity));
    }

    private RoleEntity getExistingRoleEntity(Role role) {
        // If role has an ID, try to find existing entity first
        if (role.getId() != null) {
            return roleEntityJpaRepository.findById(role.getId())
                    .orElseThrow(() -> new IllegalStateException("Role with ID " + role.getId() + " not found"));
        }

        // If role has a code, try to find by code
        if (role.getCode() != null) {
            return roleEntityJpaRepository.findByCode(role.getCode())
                    .orElseThrow(() -> new IllegalStateException("Role with code " + role.getCode() + " not found"));
        }

        // Fallback to creating new entity (this should not happen in normal flow)
        throw new IllegalStateException("Role must have either ID or code");
    }

    @Override
    public User getUserByEmail(String email) {
        return userEntityJpaRepository.findByEmail(email)
                .map(UserEntityMapper::toModel)
                .orElse(null);
    }

}
