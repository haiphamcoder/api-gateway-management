package io.github.haiphamcoder.gateway.layer.presentation.rest.dto.response;

import java.time.Instant;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceInfoResponse {

    @JsonProperty("service")
    private ServiceDetails service;

    @JsonProperty("system")
    private SystemInfo system;

    @JsonProperty("timestamp")
    private Instant timestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceDetails {
        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("version")
        private String version;

        @JsonProperty("status")
        private String status;

        @JsonProperty("uptime")
        private String uptime;

        @JsonProperty("environment")
        private String environment;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SystemInfo {
        @JsonProperty("java_version")
        private String javaVersion;

        @JsonProperty("java_vendor")
        private String javaVendor;

        @JsonProperty("os_name")
        private String osName;

        @JsonProperty("os_version")
        private String osVersion;

        @JsonProperty("os_arch")
        private String osArch;

        @JsonProperty("available_processors")
        private Integer availableProcessors;

        @JsonProperty("total_memory")
        private String totalMemory;

        @JsonProperty("free_memory")
        private String freeMemory;

        @JsonProperty("max_memory")
        private String maxMemory;

        @JsonProperty("used_memory")
        private String usedMemory;

        @JsonProperty("memory_usage_percent")
        private Double memoryUsagePercent;
    }

}
