package io.github.haiphamcoder.gateway.layer.application.repository;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.User;

public interface UserRepository {

    User createUser(User user);

    User getUserByEmail(String email);

    User getUserByEmailWithRoles(String email);

    User getUserByIdWithRoles(Long id);
    
}
