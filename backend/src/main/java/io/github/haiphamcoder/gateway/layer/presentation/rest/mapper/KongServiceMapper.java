package io.github.haiphamcoder.gateway.layer.presentation.rest.mapper;

import io.github.haiphamcoder.gateway.layer.application.domain.model.KongService;
import io.github.haiphamcoder.gateway.layer.presentation.rest.dto.request.KongServiceRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class KongServiceMapper {

    public KongServiceRequest toKongServiceRequest(KongService service) {
        return KongServiceRequest.builder()
                .name(service.getName())
                .url(service.getUrl())
                .connectTimeout(service.getConnectTimeout())
                .writeTimeout(service.getWriteTimeout())
                .readTimeout(service.getReadTimeout())
                .retries(service.getRetries())
                .tags(service.getTags())
                .build();
    }

    public KongService toKongService(KongServiceRequest request) {
        return KongService.builder()
                .name(request.getName())
                .url(request.getUrl())
                .connectTimeout(request.getConnectTimeout())
                .writeTimeout(request.getWriteTimeout())
                .readTimeout(request.getReadTimeout())
                .retries(request.getRetries())
                .tags(request.getTags())
                .build();
    }
    
}
