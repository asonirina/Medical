package com.by.iason.exception;

import com.by.iason.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

/**
 * Created by iason
 * on 10/18/2017.
 */

@RestControllerAdvice(basePackages = "com.by.iason")
public class ErrorHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        return handle(HttpStatus.FORBIDDEN, "Access Denied");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception ex) {
        return handle(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong...");
    }

    private ErrorResponse handle(HttpStatus status, String message) {
        return new ErrorResponse(message, status.value());
    }
}
