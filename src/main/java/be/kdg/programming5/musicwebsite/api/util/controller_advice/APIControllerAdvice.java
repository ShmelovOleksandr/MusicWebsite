package be.kdg.programming5.musicwebsite.api.util.controller_advice;

import be.kdg.programming5.musicwebsite.util.exception.ArtistNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
//@ControllerAdvice(basePackages = { "be.kdg.programming5.musicwebsite.api.controller" })
public class APIControllerAdvice {
    @ExceptionHandler(value = { ArtistNotFoundException.class })
    public ErrorResponse handleArtistNotFoundException(ArtistNotFoundException ex, WebRequest request) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    }

}
