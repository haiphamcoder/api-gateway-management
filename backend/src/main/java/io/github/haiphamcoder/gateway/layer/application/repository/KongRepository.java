package io.github.haiphamcoder.gateway.layer.application.repository;

import java.util.List;

import io.github.haiphamcoder.gateway.layer.application.domain.model.KongRoute;
import io.github.haiphamcoder.gateway.layer.application.domain.model.KongService;
import io.github.haiphamcoder.gateway.layer.application.domain.model.KongTarget;
import io.github.haiphamcoder.gateway.layer.application.domain.model.KongUpstream;

public interface KongRepository {

    KongService createService(KongService service);

    KongService getService(String id);

    List<KongService> getServices(String search, Integer page, Integer size);

    KongService updateService(KongService service);

    void deleteService(String id);

    KongRoute createRoute(String serviceId, KongRoute route);

    KongRoute getRoute(String id);

    List<KongRoute> getRoutes();

    KongRoute updateRoute(String id, KongRoute route);

    void deleteRoute(String id);

    KongUpstream createUpstream(KongUpstream upstream);

    KongUpstream getUpstream(String id);

    List<KongUpstream> getUpstreams();

    KongUpstream updateUpstream(String id, KongUpstream upstream);

    void deleteUpstream(String id);

    KongTarget createTarget(String upstreamId, KongTarget target);

    KongTarget getTarget(String id);

    List<KongTarget> getTargets();

    KongTarget updateTarget(String id, KongTarget target);

    void deleteTarget(String id);

}
