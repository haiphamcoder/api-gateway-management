package io.github.haiphamcoder.gateway.layer.application.domain.model.auth;

import java.time.Instant;
import java.util.Set;

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
public class User {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password_hash")
    private String passwordHash;

    @JsonProperty("name")
    private String name;

    @JsonProperty("locale")
    @Builder.Default
    private String locale = "en";

    @JsonProperty("status")
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @JsonProperty("roles")
    private Set<Role> roles;

    @JsonProperty("created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();

    @JsonProperty("updated_at")
    @Builder.Default
    private Instant updatedAt = Instant.now();

    public enum UserStatus {
        ACTIVE, INACTIVE, SUSPENDED, PENDING_VERIFICATION
    }

}
