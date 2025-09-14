package io.github.haiphamcoder.gateway.layer.application.usecase;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.LoginResponse;

public interface AuthenticationManagementUsecase {

    LoginResponse login(String email, String password, boolean isRememberMe);

    LoginResponse refreshToken(String token);

    void logout(Long userId);

}
