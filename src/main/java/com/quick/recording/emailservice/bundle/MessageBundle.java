package com.quick.recording.emailservice.bundle;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageBundle {

    private static final String EXCEPTION_BUNDLE = "properties.exception";
    private static final ResourceBundle EXCEPTION_RESOURCE_BUNDLE = ResourceBundle.getBundle(EXCEPTION_BUNDLE, new ResourceBundleControl());

    private MessageBundle() {
    }

    public static String getValue(String property) {
        return getValue(property, EXCEPTION_RESOURCE_BUNDLE.getString("base.error"));
    }

    public static String getValue(String property, String defaultProperty) {
        try {
            return EXCEPTION_RESOURCE_BUNDLE.getString(property);
        } catch (MissingResourceException e) {
            return defaultProperty;
        }
    }

}
