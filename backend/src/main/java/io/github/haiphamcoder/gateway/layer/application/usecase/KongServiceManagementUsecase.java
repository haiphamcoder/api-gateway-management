package io.github.haiphamcoder.gateway.layer.application.usecase;

import java.util.List;

import io.github.haiphamcoder.gateway.layer.application.domain.model.KongService;

public interface KongServiceManagementUsecase {

    List<KongService> getKongServices(String search, Integer page, Integer size);

    KongService getKongService(String id);

    KongService createKongService(KongService kongService);

    KongService updateKongService(String id, KongService kongService);

    void deleteKongService(String id);

}
