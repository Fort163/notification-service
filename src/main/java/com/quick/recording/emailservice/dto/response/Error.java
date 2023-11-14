package com.quick.recording.emailservice.dto.response;

import com.quick.recording.emailservice.exception.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

@Data
public class Error implements Serializable {

    HttpStatus status;
    LocalDateTime timestamp = LocalDateTime.now();
    String message;
    Map<String, String> errors;

    public Error(BindException e) {
        status = HttpStatus.BAD_REQUEST;
        message = e.getMessage();
        errors = getErrors(e);
    }

    public Error(ResourceNotFoundException e) {
        status = HttpStatus.NOT_FOUND;
        message = e.getMessage();
    }

    public Error(MessagingException e) {
        status = HttpStatus.BAD_REQUEST;
        message = e.getMessage();
    }

    public Error(Throwable e) {
        status = HttpStatus.BAD_REQUEST;
        message = e.getMessage();
    }

    private Map<String, String> getErrors(BindException be) {
        Map<String, String> map = new TreeMap<>();
        for (ObjectError error : be.getBindingResult().getAllErrors()) {
            map.put(error.getCodes()[0].substring(error.getCodes()[0].lastIndexOf(".") + 1), error.getDefaultMessage());
        }
        return map;
    }
}
