package com.quick.recording.emailservice.exception;

import com.quick.recording.emailservice.bundle.MessageBundle;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String property) {
        super(MessageBundle.getValue(property));
    }

    public ResourceNotFoundException(String property, Object... object) {
        super(String.format(MessageBundle.getValue(property), object));
    }

}