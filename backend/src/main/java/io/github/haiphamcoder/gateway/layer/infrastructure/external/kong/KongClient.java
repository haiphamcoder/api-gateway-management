package io.github.haiphamcoder.gateway.layer.infrastructure.external.kong;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.haiphamcoder.gateway.layer.application.domain.exception.ConflictException;
import io.github.haiphamcoder.gateway.layer.application.domain.exception.NotFoundException;
import io.github.haiphamcoder.gateway.layer.application.domain.model.KongRoute;
import io.github.haiphamcoder.gateway.layer.application.domain.model.KongService;
import io.github.haiphamcoder.gateway.layer.application.domain.model.KongServiceList;
import io.github.haiphamcoder.gateway.layer.application.domain.model.KongTarget;
import io.github.haiphamcoder.gateway.layer.application.domain.model.KongUpstream;
import io.github.haiphamcoder.gateway.layer.application.repository.KongRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KongClient implements KongRepository {

    private static final String SERVICES_PATH = "/services";
    private static final String ID_FIELD = "{id}";

    private WebClient webClient;
    private int timeout;
    private int retryAttempts;

    public KongClient(
            @Value("${kong.admin.url}") String kongAdminUrl,
            @Value("${kong.admin.timeout}") int timeout,
            @Value("${kong.admin.retryAttempts}") int retryAttempts) {
        this.webClient = WebClient.builder()
                .baseUrl(kongAdminUrl)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                .build();
        this.timeout = timeout;
        this.retryAttempts = retryAttempts;
    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> {
                    log.error("Kong API Error: {} - {}", response.statusCode(), body);
                    if (response.statusCode() == HttpStatusCode.valueOf(404)) {
                        return Mono.error(new NotFoundException());
                    } else if (response.statusCode() == HttpStatusCode.valueOf(409)) {
                        return Mono.error(new ConflictException());
                    }
                    return Mono.error(new RuntimeException("Kong API Error: " + response.statusCode() + " - " + body));
                });
    }

    @Override
    public KongService createService(KongService service) {
        Mono<KongService> response = webClient.post()
                .uri(SERVICES_PATH)
                .bodyValue(service)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(KongService.class)
                .timeout(Duration.ofMillis(timeout))
                .retry(retryAttempts);
        return response.block();
    }

    @Override
    public KongService getService(String id) {
        Mono<KongService> response = webClient.get()
                .uri(SERVICES_PATH + "/" + ID_FIELD, id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(KongService.class)
                .timeout(Duration.ofMillis(timeout))
                .retry(retryAttempts);
        return response.block();
    }

    @Override
    public List<KongService> getServices(String search, Integer page, Integer size) {
        Mono<KongServiceList> response = webClient.get()
                .uri(SERVICES_PATH + "?search={search}&page={page}&size={size}", search, page, size)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(KongServiceList.class)
                .timeout(Duration.ofMillis(timeout))
                .retry(retryAttempts);
        KongServiceList result = response.block();
        return result != null ? result.getData() : Collections.emptyList();
    }

    @Override
    public KongService updateService(KongService service) {
        Mono<KongService> response = webClient.post()
                .uri(SERVICES_PATH + "/" + ID_FIELD, service.getId())
                .bodyValue(service)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(KongService.class)
                .timeout(Duration.ofMillis(timeout))
                .retry(retryAttempts);
        return response.block();
    }

    @Override
    public void deleteService(String id) {
        Mono<Void> response = webClient.delete()
                .uri(SERVICES_PATH + "/" + ID_FIELD, id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(Void.class)
                .timeout(Duration.ofMillis(timeout))
                .retry(retryAttempts);
        response.block();
    }

    @Override
    public KongRoute createRoute(String serviceId, KongRoute route) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRoute'");
    }

    @Override
    public KongRoute getRoute(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoute'");
    }

    @Override
    public List<KongRoute> getRoutes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoutes'");
    }

    @Override
    public KongRoute updateRoute(String id, KongRoute route) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRoute'");
    }

    @Override
    public void deleteRoute(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRoute'");
    }

    @Override
    public KongUpstream createUpstream(KongUpstream upstream) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUpstream'");
    }

    @Override
    public KongUpstream getUpstream(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUpstream'");
    }

    @Override
    public List<KongUpstream> getUpstreams() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUpstreams'");
    }

    @Override
    public KongUpstream updateUpstream(String id, KongUpstream upstream) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUpstream'");
    }

    @Override
    public void deleteUpstream(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUpstream'");
    }

    @Override
    public KongTarget createTarget(String upstreamId, KongTarget target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTarget'");
    }

    @Override
    public KongTarget getTarget(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTarget'");
    }

    @Override
    public List<KongTarget> getTargets() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTargets'");
    }

    @Override
    public KongTarget updateTarget(String id, KongTarget target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTarget'");
    }

    @Override
    public void deleteTarget(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTarget'");
    }

}
