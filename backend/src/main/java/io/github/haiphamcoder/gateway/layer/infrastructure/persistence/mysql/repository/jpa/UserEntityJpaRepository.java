package io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.entity.UserEntity;

@Repository
public interface UserEntityJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithRoles(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<UserEntity> findByIdWithRoles(@Param("id") Long id);
    
}
