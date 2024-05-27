package be.kdg.programming5.musicwebsite.api.util.controller_advice;

import be.kdg.programming5.musicwebsite.util.exception.ArtistNotFoundException;
import be.kdg.programming5.musicwebsite.util.exception.EntityDeletionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;

@ControllerAdvice(basePackages = { "be.kdg.programming5.musicwebsite.api.controller" })
public class APIControllerAdvice {
    @ExceptionHandler(value = { ArtistNotFoundException.class })
    public ErrorResponse handleArtistNotFoundException(ArtistNotFoundException ex, WebRequest request) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    }
    @ExceptionHandler(value = { AccessDeniedException.class })
    public ErrorResponse handleArtistNotFoundException(AccessDeniedException ex, WebRequest request) {
        return ErrorResponse.create(ex, HttpStatus.FORBIDDEN, ex.getLocalizedMessage());
    }
    @ExceptionHandler(value = { EntityDeletionException.class })
    private ErrorResponse handleDeletionExceptions(EntityDeletionException ex, WebRequest request) {
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete the the entity.");
    }
}
