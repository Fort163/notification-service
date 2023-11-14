package com.quick.recording.emailservice.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Success implements Serializable {

    HttpStatus status = HttpStatus.OK;
    LocalDateTime timestamp = LocalDateTime.now();
    String message;

    public Success(String message) {
        this.message = message;
    }

}
