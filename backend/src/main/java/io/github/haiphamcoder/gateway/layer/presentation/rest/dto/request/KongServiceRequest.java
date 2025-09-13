package io.github.haiphamcoder.gateway.layer.presentation.rest.dto.request;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KongServiceRequest {

    @NotBlank(message = "Service name is required")
    @Pattern(regexp = "^[A-Za-z0-9.\\-]+$", message = "Service name must contain only alphanumeric characters, dots, and hyphens")
    private String name;
    
    @NotBlank(message = "URL is required")
    private String url;
    
    @Min(value = 1, message = "Connect timeout must be at least 1ms")
    @Max(value = 120000, message = "Connect timeout must not exceed 120000ms")
    @JsonProperty("connect_timeout")
    @Builder.Default
    private Integer connectTimeout = 30000;
    
    @Min(value = 1, message = "Write timeout must be at least 1ms")
    @Max(value = 120000, message = "Write timeout must not exceed 120000ms")
    @JsonProperty("write_timeout")
    @Builder.Default
    private Integer writeTimeout = 30000;
    
    @Min(value = 1, message = "Read timeout must be at least 1ms")
    @Max(value = 120000, message = "Read timeout must not exceed 120000ms")
    @JsonProperty("read_timeout")
    @Builder.Default
    private Integer readTimeout = 30000;
    
    @Min(value = 0, message = "Retries must be non-negative")
    @Max(value = 10, message = "Retries must not exceed 10")
    @JsonProperty("retries")
    @Builder.Default
    private Integer retries = 5;
    
    private Map<String, Object> tags;
    
}
