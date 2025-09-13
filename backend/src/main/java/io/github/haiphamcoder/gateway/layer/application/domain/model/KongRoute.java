package io.github.haiphamcoder.gateway.layer.application.domain.model;

import java.time.Instant;
import java.util.List;
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
public class KongRoute {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("protocols")
    private List<String> protocols;

    @JsonProperty("methods")
    private List<String> methods;

    @JsonProperty("hosts")
    private List<String> hosts;

    @JsonProperty("paths")
    private List<String> paths;

    @JsonProperty("regex_priority")
    private String regexPriority;

    @JsonProperty("strip_path")
    private Boolean stripPath;

    @JsonProperty("preserve_host")
    private Boolean preserveHost;

    @JsonProperty("request_buffering")
    private Boolean requestBuffering;

    @JsonProperty("response_buffering")
    private Boolean responseBuffering;

    @JsonProperty("tags")
    private Map<String, Object> tags;

    @JsonProperty("service")
    private ServiceReference service;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ServiceReference {

        @JsonProperty("id")
        private String id;

    }

}
