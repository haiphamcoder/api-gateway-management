package io.github.haiphamcoder.gateway.layer.application.domain.model;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KongUpstream {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("algorithm")
    private String algorithm;

    @JsonProperty("slots")
    private Integer slots;

    @JsonProperty("orderlist")
    private Integer orderlist;

    @JsonProperty("tags")
    private Map<String, Object> tags;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    @JsonProperty("healthchecks")
    private HealthChecks healthchecks;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HealthChecks {

        @JsonProperty("active")
        private ActiveHealthCheck active;

        @JsonProperty("passive")
        private PassiveHealthCheck passive;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ActiveHealthCheck {

        @JsonProperty("type")
        @Builder.Default
        private String type = "http";

        @JsonProperty("https_verify_certificate")
        @Builder.Default
        private Boolean httpsVerifyCertificate = false;

        @JsonProperty("http_path")
        @Builder.Default
        private String httpPath = "/";

        @JsonProperty("timeout")
        @Builder.Default
        private Integer timeout = 1;

        @JsonProperty("concurrency")
        @Builder.Default
        private Integer concurrency = 10;

        @JsonProperty("healthy")
        private HealthyConfig healthy;

        @JsonProperty("unhealthy")
        private UnhealthyConfig unhealthy;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PassiveHealthCheck {

        @JsonProperty("type")
        @Builder.Default
        private String type = "http";

        @JsonProperty("healthy")
        private HealthyConfig healthy;

        @JsonProperty("unhealthy")
        private UnhealthyConfig unhealthy;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HealthyConfig {

        @JsonProperty("interval")
        @Builder.Default
        private Integer interval = 0;

        @JsonProperty("success")
        @Builder.Default
        private Integer success = 0;

        @JsonProperty("http_statuses")
        private Integer httpStatuses;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UnhealthyConfig {

        @JsonProperty("interval")
        @Builder.Default
        private Integer interval = 0;

        @JsonProperty("timeouts")
        @Builder.Default
        private Integer timeouts = 0;

        @JsonProperty("http_failures")
        @Builder.Default
        private Integer httpFailures = 0;

        @JsonProperty("tcp_failures")
        @Builder.Default
        private Integer tcpFailures = 0;

        @JsonProperty("http_statuses")
        private Integer httpStatuses;
    }

}
