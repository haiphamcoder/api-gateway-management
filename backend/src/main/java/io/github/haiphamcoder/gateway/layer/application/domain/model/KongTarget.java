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
public class KongTarget {

    @JsonProperty("id")
    private String id;

    @JsonProperty("target")
    private String target;

    @JsonProperty("weight")
    private Integer weight;

    @JsonProperty("upstream")
    private String upstream;

    @JsonProperty("tags")
    private Map<String, Object> tags;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;
    
}
