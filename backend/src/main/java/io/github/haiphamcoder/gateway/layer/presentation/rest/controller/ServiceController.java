package io.github.haiphamcoder.gateway.layer.presentation.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.haiphamcoder.gateway.layer.application.domain.model.KongService;
import io.github.haiphamcoder.gateway.layer.application.usecase.KongServiceManagementUsecase;
import io.github.haiphamcoder.gateway.layer.presentation.rest.dto.request.KongServiceRequest;
import io.github.haiphamcoder.gateway.layer.presentation.rest.mapper.KongServiceMapper;
import io.github.haiphamcoder.gateway.utility.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class ServiceController {

    private final KongServiceManagementUsecase kongServiceManagementUsecase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<KongService>>> getServices(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(ApiResponse.success(
                kongServiceManagementUsecase.getKongServices(search, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KongService>> getService(
            @PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(kongServiceManagementUsecase.getKongService(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<KongService>> createService(
            @Valid @RequestBody KongServiceRequest kongService) {
        return ResponseEntity.ok(ApiResponse.success(kongServiceManagementUsecase.createKongService(KongServiceMapper.toKongService(kongService))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KongService>> updateService(
            @PathVariable String id,
            @Valid @RequestBody KongServiceRequest kongService) {
        return ResponseEntity.ok(ApiResponse.success(kongServiceManagementUsecase.updateKongService(id, KongServiceMapper.toKongService(kongService))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteService(
            @PathVariable String id) {
        kongServiceManagementUsecase.deleteKongService(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
