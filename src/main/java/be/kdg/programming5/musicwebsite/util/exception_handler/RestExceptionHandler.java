package be.kdg.programming5.musicwebsite.util.exception_handler;

import be.kdg.programming5.musicwebsite.util.exception.EntityDeletionException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {
    private final Logger logger;

    public RestExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    private ErrorResponse handleNotFoundExceptions(EntityNotFoundException ex, WebRequest request) {
        logger.error("An exception has been handled. Details: " + ex.getMessage());
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = { EntityDeletionException.class })
    private ErrorResponse handleDeletionExceptions(EntityDeletionException ex, WebRequest request) {
        logger.error("An exception has been handled. Details: " + ex.getMessage());
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete the the entity.");
    }

}
