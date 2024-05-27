package be.kdg.programming5.musicwebsite.util.exception_handler;

import com.google.gson.JsonParseException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@ControllerAdvice(basePackages = { "be.kdg.programming5.musicwebsite.controller" })
public class GlobalExceptionHandler {
    @Value("${sql-exception.message}")
    private String SQL_EXCEPTION_MESSAGE;

    @Value("${json-exception.message}")
    private String JSON_EXCEPTION_MESSAGE;
    @Value("${unknown-exception.message}")
    private String UNKNOWN_EXCEPTION_MESSAGE;

    private final Logger logger;

    @Autowired
    public GlobalExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    private ModelAndView handleNotFoundExceptions(EntityNotFoundException ex, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.getModel().put("errorCode", HttpStatus.NOT_FOUND);
        modelAndView.getModel().put("errorMessage", ex.getLocalizedMessage());

        modelAndView.setViewName("view/errors/exception");
        logger.error("An exception has been handled. Details: " + ex.getMessage());
        return modelAndView;
    }



    @ExceptionHandler(value = { JsonParseException.class })
    private ModelAndView handleJsonException(JsonParseException ex, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.getModel().put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.getModel().put("errorMessage", JSON_EXCEPTION_MESSAGE);

        modelAndView.setViewName("view/errors/exception");
        logger.error("An exception has been handled. Details: " + ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = { SQLException.class })
    private ModelAndView handleSQLException(SQLException ex, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.getModel().put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.getModel().put("errorMessage", SQL_EXCEPTION_MESSAGE);

        modelAndView.setViewName("view/errors/exception");
        logger.error("An exception has been handled. Details: " + ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = { RuntimeException.class })
    private ModelAndView handleSQLException(RuntimeException ex, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.getModel().put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.getModel().put("errorMessage", UNKNOWN_EXCEPTION_MESSAGE);

        modelAndView.setViewName("view/errors/exception");
        logger.error("An exception has been handled. Details: " + ex.getMessage());
        return modelAndView;
    }
}
