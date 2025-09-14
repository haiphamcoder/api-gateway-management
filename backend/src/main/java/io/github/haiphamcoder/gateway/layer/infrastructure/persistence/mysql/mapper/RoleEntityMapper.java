package io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.mapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.Role;
import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.Role.RoleBuilder;
import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.User;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.entity.RoleEntity;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.entity.UserEntity;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class RoleEntityMapper {

    public static RoleEntity toEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .code(role.getCode())
                .description(role.getDescription())
                .users(role.getUsers() != null
                        ? role.getUsers().stream().map(UserEntityMapper::toEntity).collect(Collectors.toSet())
                        : null)
                .createdAt(role.getCreatedAt() != null ? new Timestamp(role.getCreatedAt().toEpochMilli()) : null)
                .updatedAt(role.getUpdatedAt() != null ? new Timestamp(role.getUpdatedAt().toEpochMilli()) : null)
                .build();
    }

    public static Role toModel(RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .code(roleEntity.getCode())
                .description(roleEntity.getDescription())
                .createdAt(roleEntity.getCreatedAt() != null ? Instant.ofEpochMilli(roleEntity.getCreatedAt().getTime()) : null)
                .updatedAt(roleEntity.getUpdatedAt() != null ? Instant.ofEpochMilli(roleEntity.getUpdatedAt().getTime()) : null)
                .build();
    }

    public static Role toModelWithUsers(RoleEntity roleEntity) {
        RoleBuilder builder = Role.builder();
        builder.id(roleEntity.getId());
        builder.code(roleEntity.getCode());
        builder.description(roleEntity.getDescription());
        builder.createdAt(
                roleEntity.getCreatedAt() != null ? Instant.ofEpochMilli(roleEntity.getCreatedAt().getTime()) : null);
        builder.updatedAt(
                roleEntity.getUpdatedAt() != null ? Instant.ofEpochMilli(roleEntity.getUpdatedAt().getTime()) : null);
        Set<UserEntity> userEntities = roleEntity.getUsers();
        if (userEntities != null && !userEntities.isEmpty()) {
            Set<User> users = new HashSet<>();
            for (UserEntity userEntity : userEntities) {
                User user = User.builder()
                        .id(userEntity.getId())
                        .email(userEntity.getEmail())
                        .name(userEntity.getName())
                        .locale(userEntity.getLocale())
                        .status(userEntity.getStatus())
                        .createdAt(userEntity.getCreatedAt() != null
                                ? Instant.ofEpochMilli(userEntity.getCreatedAt().getTime())
                                : null)
                        .updatedAt(userEntity.getUpdatedAt() != null
                                ? Instant.ofEpochMilli(userEntity.getUpdatedAt().getTime())
                                : null)
                        .build();
                users.add(user);
            }
            builder.users(users);
        }
        return builder.build();
    }

}
