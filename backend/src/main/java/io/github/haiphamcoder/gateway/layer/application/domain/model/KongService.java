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
public class KongService {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("host")
    private String host;

    @JsonProperty("port")
    private Integer port;

    @JsonProperty("path")
    private String path;

    @JsonProperty("connect_timeout")
    private Integer connectTimeout;

    @JsonProperty("write_timeout")
    private Integer writeTimeout;

    @JsonProperty("read_timeout")
    private Integer readTimeout;

    @JsonProperty("retries")
    private Integer retries;

    @JsonProperty("tags")
    private Map<String, Object> tags;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

}
