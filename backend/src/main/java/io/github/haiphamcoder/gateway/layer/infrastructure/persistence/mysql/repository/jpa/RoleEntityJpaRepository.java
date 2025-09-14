package io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.entity.RoleEntity;

@Repository
public interface RoleEntityJpaRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByCode(String code);

    boolean existsByCode(String code);
    
}
