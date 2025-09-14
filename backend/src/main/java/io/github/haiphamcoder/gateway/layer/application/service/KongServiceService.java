package io.github.haiphamcoder.gateway.layer.application.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.github.haiphamcoder.gateway.layer.application.domain.model.kong.KongService;
import io.github.haiphamcoder.gateway.layer.application.repository.KongRepository;
import io.github.haiphamcoder.gateway.layer.application.usecase.KongServiceManagementUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KongServiceService implements KongServiceManagementUsecase {

    private final KongRepository kongRepository;

    @Override
    @Cacheable(value = "kongServices", key = "#search + #page + #size")
    public List<KongService> getKongServices(String search, Integer page, Integer size) {
        return kongRepository.getServices(search, page, size);
    }

    @Override
    @Cacheable(value = "kongService", key = "#id")
    public KongService getKongService(String id) {
        return kongRepository.getService(id);
    }

    @Override
    public KongService createKongService(KongService kongService) {
        return kongRepository.createService(kongService);
    }

    @Override
    public KongService updateKongService(String id, KongService kongService) {
        return kongRepository.updateService(kongService);
    }

    @Override
    public void deleteKongService(String id) {
        kongRepository.deleteService(id);
    }

}
