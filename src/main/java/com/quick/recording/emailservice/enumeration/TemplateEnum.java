package com.quick.recording.emailservice.enumeration;

import java.util.List;

public enum TemplateEnum {

    TEXT("text.html", getText()),
    REGISTRATION("registration.html", getRegistration());

    private String name;
    private List<String> images;

    TemplateEnum(String name, List<String> images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public List<String> getImages() {
        return images;
    }

    private static List<String> getRegistration() {
        return List.of("/images/logo_good_news.png");
    }

    private static List<String> getText() {
        return List.of("/images/logo_good_news.png");
    }

}
