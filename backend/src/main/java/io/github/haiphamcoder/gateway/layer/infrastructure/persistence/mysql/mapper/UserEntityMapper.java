package io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.mapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.Role;
import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.User;
import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.User.UserBuilder;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.entity.RoleEntity;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.entity.UserEntity;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class UserEntityMapper {

        public static UserEntity toEntity(User user) {
                return UserEntity.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .passwordHash(user.getPasswordHash())
                                .name(user.getName())
                                .locale(user.getLocale())
                                .status(user.getStatus())
                                .roles(user.getRoles() != null
                                                ? user.getRoles().stream().map(RoleEntityMapper::toEntity)
                                                                .collect(Collectors.toSet())
                                                : null)
                                .createdAt(user.getCreatedAt() != null
                                                ? new Timestamp(user.getCreatedAt().toEpochMilli())
                                                : null)
                                .updatedAt(user.getUpdatedAt() != null
                                                ? new Timestamp(user.getUpdatedAt().toEpochMilli())
                                                : null)
                                .build();
        }

        public static User toModelWithRoles(UserEntity userEntity) {
                UserBuilder builder = User.builder();
                builder.id(userEntity.getId());
                builder.email(userEntity.getEmail());
                builder.passwordHash(userEntity.getPasswordHash());
                builder.name(userEntity.getName());
                builder.locale(userEntity.getLocale());
                builder.status(userEntity.getStatus());
                builder.createdAt(userEntity.getCreatedAt() != null
                                ? Instant.ofEpochMilli(userEntity.getCreatedAt().getTime())
                                : null);
                builder.updatedAt(userEntity.getUpdatedAt() != null
                                ? Instant.ofEpochMilli(userEntity.getUpdatedAt().getTime())
                                : null);
                Set<RoleEntity> roleEntities = userEntity.getRoles();
                if (roleEntities != null && !roleEntities.isEmpty()) {
                        Set<Role> roles = new HashSet<>();
                        for (RoleEntity roleEntity : roleEntities) {
                                Role role = Role.builder()
                                        .id(roleEntity.getId())
                                        .code(roleEntity.getCode())
                                        .description(roleEntity.getDescription())
                                        .createdAt(roleEntity.getCreatedAt() != null ? Instant.ofEpochMilli(roleEntity.getCreatedAt().getTime()) : null)
                                        .updatedAt(roleEntity.getUpdatedAt() != null ? Instant.ofEpochMilli(roleEntity.getUpdatedAt().getTime()) : null)
                                        .build();
                                roles.add(role);
                        }
                        builder.roles(roles);
                }
                return builder.build();
        }

        public static User toModel(UserEntity userEntity) {
                return User.builder()
                        .id(userEntity.getId())
                        .email(userEntity.getEmail())
                        .passwordHash(userEntity.getPasswordHash())
                        .name(userEntity.getName())
                        .locale(userEntity.getLocale())
                        .status(userEntity.getStatus())
                        .createdAt(userEntity.getCreatedAt() != null ? Instant.ofEpochMilli(userEntity.getCreatedAt().getTime()) : null)
                        .updatedAt(userEntity.getUpdatedAt() != null ? Instant.ofEpochMilli(userEntity.getUpdatedAt().getTime()) : null)
                        .build();
        }

}
