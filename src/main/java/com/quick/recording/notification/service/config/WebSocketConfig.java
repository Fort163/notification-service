package com.quick.recording.notification.service.config;

import com.quick.recording.notification.service.security.WebSocketAuthentication;
import com.quick.recording.resource.service.security.QROAuth2AuthenticatedPrincipal;
import com.quick.recording.resource.service.security.QROpaqueTokenIntrospector;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final QROpaqueTokenIntrospector introspector;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/queue", "/topic")
                .setUserDestinationBroadcast("/queue/unresolved-user-destination")
                .setUserRegistryBroadcast("/queue/simp-user-registry")
                .setAutoStartup(true)
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        registry.setApplicationDestinationPrefixes("/qr-app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/v1/notification/socket")
                .setAllowedOrigins("http://auth-service:3001",
                        "http://localhost:3001",
                        "https://www.quick-peter-calendar.ru",
                        "https://quick-peter-calendar.ru")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> tokenList = accessor.getNativeHeader("Authorization");
                    String token = null;
                    if(tokenList == null || tokenList.isEmpty()) {
                        return message;
                    }
                    else {
                        token = tokenList.get(0).replace("Bearer", "").trim();
                    }
                    QROAuth2AuthenticatedPrincipal wsPrincipal = (QROAuth2AuthenticatedPrincipal) introspector.introspect(token);
                    WebSocketAuthentication authentication = new WebSocketAuthentication(wsPrincipal);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    accessor.setUser(authentication);
                    accessor.setLeaveMutable(true);
                    return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
                }
                return message;
            }
        });
    }

    /*
    //AbstractSecurityWebSocketMessageBrokerConfigurer

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.simpTypeMatchers(SimpMessageType.CONNECT,SimpMessageType.DISCONNECT, SimpMessageType.OTHER).permitAll()
                .simpMessageDestMatchers("/client/**").permitAll()
                .simpMessageDestMatchers("/server/**").authenticated();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
     */

}
