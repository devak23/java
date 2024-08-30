package com.rnd.app.strategyasmap.config.notification;

import com.rnd.app.strategyasmap.service.notification.NotificationService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class NotificationServiceFactory {
    private final Map<String, NotificationService> notificationServices;


    public NotificationServiceFactory(Map<String, NotificationService> notificationServices) {
        this.notificationServices = notificationServices;
    }

    private Optional<NotificationService> getNotificationService(String notificationType) {
        return Optional.ofNullable(notificationServices.get(notificationType));
    }

    public String sendNotification(String notificationType) {
        return getNotificationService(notificationType)
                .map(NotificationService::sendNotification)
                .orElseThrow();

    }
}
