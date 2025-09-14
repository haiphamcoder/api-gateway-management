package io.github.haiphamcoder.gateway.layer.application.domain.model.auth;

import java.time.Instant;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("description")
    private String description;

    @JsonProperty("users")
    private Set<User> users;

    @JsonProperty("created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();

    @JsonProperty("updated_at")
    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Builder
    public Role(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Getter
    @AllArgsConstructor
    public enum RoleCode {
        ADMIN("ADMIN", "System administrator"),
        OWNER("OWNER", "Resource owner"),
        VIEWER("VIEWER", "Read-only access"),
        EDITOR("EDITOR", "Read and write access");

        private String code;
        private String description;

    }

}
