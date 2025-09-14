package io.github.haiphamcoder.gateway.layer.presentation.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.haiphamcoder.gateway.layer.application.domain.model.auth.LoginResponse;
import io.github.haiphamcoder.gateway.layer.application.usecase.AuthenticationManagementUsecase;
import io.github.haiphamcoder.gateway.layer.presentation.rest.dto.request.LoginRequest;
import io.github.haiphamcoder.gateway.layer.presentation.rest.dto.request.RefreshTokenRequest;
import io.github.haiphamcoder.gateway.utility.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManagementUsecase authenticationManagementUsecase;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(ApiResponse.success(authenticationManagementUsecase.login(loginRequest.getEmail(),
                loginRequest.getPassword(), loginRequest.isRememberMe())));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(ApiResponse
                .success(authenticationManagementUsecase.refreshToken(refreshTokenRequest.getRefreshToken())));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestHeader("X-User-Id") Long userId) {
        authenticationManagementUsecase.logout(userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
