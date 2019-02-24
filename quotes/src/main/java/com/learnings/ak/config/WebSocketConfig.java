package com.learnings.ak.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry brokerRegistry) {
        brokerRegistry.enableSimpleBroker("/topic");
        brokerRegistry.setApplicationDestinationPrefixes("/quotesApp");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompRegistry) {
        stompRegistry.addEndpoint("/quote-fetcher").withSockJS();
    }
}
