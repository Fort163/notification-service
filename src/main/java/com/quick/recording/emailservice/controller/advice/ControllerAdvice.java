package com.quick.recording.emailservice.controller.advice;

import com.quick.recording.emailservice.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BindException.class)
    public Error handleBindException(BindException e) {
        return new Error(e);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Error handleBindException(ResourceNotFoundException e) {
        return new Error(e);
    }

    @ExceptionHandler(MessagingException.class)
    public Error handleMessagingException(MessagingException e) {
        return new Error(e);
    }

    @ExceptionHandler(Throwable.class)
    public Error handleThrowable(Throwable e) {
        return new Error(e);
    }

}
