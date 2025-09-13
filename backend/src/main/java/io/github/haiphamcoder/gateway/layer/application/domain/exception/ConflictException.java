package io.github.haiphamcoder.gateway.layer.application.domain.exception;

public class ConflictException extends RuntimeException {

    public ConflictException() {
        super("Resource already exists");
    }

    public ConflictException(String message) {
        super(message);
    }
    
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }
    
}
