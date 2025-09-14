package io.github.haiphamcoder.gateway.layer.application.repository;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.Role;

public interface RoleRepository {

    Role getRoleByCode(String code);

    boolean existsByCode(String code);

    Role createRole(Role role);
    
}
