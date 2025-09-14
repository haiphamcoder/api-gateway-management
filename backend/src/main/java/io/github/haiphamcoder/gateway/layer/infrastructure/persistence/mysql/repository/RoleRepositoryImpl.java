package io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.Role;
import io.github.haiphamcoder.gateway.layer.application.repository.RoleRepository;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.mapper.RoleEntityMapper;
import io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.repository.jpa.RoleEntityJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleEntityJpaRepository roleEntityJpaRepository;

    @Override
    public boolean existsByCode(String code) {
        return roleEntityJpaRepository.existsByCode(code);
    }

    @Override
    @Transactional
    @Modifying
    public Role createRole(Role role) {
        return RoleEntityMapper.toModel(roleEntityJpaRepository.save(RoleEntityMapper.toEntity(role)));
    }

    @Override
    public Role getRoleByCode(String code) {
        return RoleEntityMapper.toModel(roleEntityJpaRepository.findByCode(code).orElse(null));
    }

}
