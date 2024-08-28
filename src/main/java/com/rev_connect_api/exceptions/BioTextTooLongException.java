package com.rev_connect_api.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for handling when bio text is too long.
 * @return code PAYLOAD_TOO_LARGE with string message
 */
@ResponseStatus(code = HttpStatus.PAYLOAD_TOO_LARGE)
public class BioTextTooLongException extends RuntimeException {
    private String message;
    public BioTextTooLongException() {

    }

    public BioTextTooLongException(String message) {
        super(message);
        this.message = message;
    }
}

