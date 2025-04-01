package com.quick.recording.notification.service.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties("notification.ws")
public class WebSocketProperty {

    private String host;
    private Integer port;
    private String login;
    private String password;
    private List<String> allowedOrigins;

}
