package io.github.haiphamcoder.gateway.utility.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * A generic API response class that can be used to return a response from the
 * API.
 * 
 * @author Hai Pham Ngoc
 * @version 1.0.0
 * @since 1.0.0
 * @param <T> The type of the data to be returned in the response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("timestamp")
    private Instant timestamp;

    /**
     * Create a successful response with data and current timestamp
     * 
     * @param <T>  The type of the data to be returned in the response.
     * @param data The data to be returned in the response.
     * @return A successful API response with the data and current timestamp.
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "OK", "Operation completed successfully", data, null, Instant.now());
    }

    /**
     * Create a successful response with data and metadata and current timestamp
     * 
     * @param <T>      The type of the data to be returned in the response.
     * @param data     The data to be returned in the response.
     * @param metadata The metadata to be returned in the response.
     * @return A successful API response with the data, metadata and current
     *         timestamp.
     */
    public static <T> ApiResponse<T> success(T data, Map<String, Object> metadata) {
        return new ApiResponse<>(true, "OK", "Operation completed successfully", data, metadata,
                Instant.now());
    }

    /**
     * Create a successful response with custom message and current timestamp
     * 
     * @param <T>     The type of the data to be returned in the response.
     * @param message The message to be returned in the response.
     * @param data    The data to be returned in the response.
     * @return A successful API response with the message and data and current
     *         timestamp.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, "OK", message, data, null, Instant.now());
    }

    /**
     * Create a successful response with custom message, data and metadata and
     * current timestamp
     * 
     * @param <T>      The type of the data to be returned in the response.
     * @param message  The message to be returned in the response.
     * @param data     The data to be returned in the response.
     * @param metadata The metadata to be returned in the response.
     * @return A successful API response with the message, data, metadata and
     *         current
     *         timestamp.
     */
    public static <T> ApiResponse<T> success(String message, T data, Map<String, Object> metadata) {
        return new ApiResponse<>(true, "OK", message, data, metadata, Instant.now());
    }

    /**
     * Create an error response with current timestamp
     * 
     * @param <T>     The type of the data to be returned in the response.
     * @param code    The code of the error.
     * @param message The message of the error.
     * @return An error API response with the code, message and current timestamp.
     */
    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(false, code, message, null, null, Instant.now());
    }

    /**
     * Create an error response with metadata and current timestamp
     * 
     * @param <T>      The type of the data to be returned in the response.
     * @param code     The code of the error.
     * @param message  The message of the error.
     * @param metadata The metadata to be returned in the response.
     * @return An error API response with the code, message, metadata and current
     *         timestamp.
     */
    public static <T> ApiResponse<T> error(String code, String message, Map<String, Object> metadata) {
        return new ApiResponse<>(false, code, message, null, metadata, Instant.now());
    }

    /**
     * Create an error response with data and current timestamp
     * 
     * @param <T>     The type of the data to be returned in the response.
     * @param code    The code of the error.
     * @param message The message of the error.
     * @param data    The data to be returned in the response.
     * @return An error API response with the code, message, data and current
     *         timestamp.
     */
    public static <T> ApiResponse<T> error(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data, null, Instant.now());
    }

    /**
     * Create an error response with data and current timestamp
     * 
     * @param <T>      The type of the data to be returned in the response.
     * @param code     The code of the error.
     * @param message  The message of the error.
     * @param data     The data to be returned in the response.
     * @param metadata The metadata to be returned in the response.
     * @return An error API response with the code, message, data, metadata and
     *         current timestamp.
     */
    public static <T> ApiResponse<T> error(String code, String message, T data, Map<String, Object> metadata) {
        return new ApiResponse<>(false, code, message, data, metadata, Instant.now());
    }

}
