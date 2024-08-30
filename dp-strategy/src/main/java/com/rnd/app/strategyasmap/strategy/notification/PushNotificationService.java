package com.rnd.app.strategyasmap.strategy.notification;

import com.rnd.app.strategyasmap.model.notification.NotificationType;
import com.rnd.app.strategyasmap.service.notification.NotificationService;
import org.springframework.stereotype.Service;

@Service(NotificationType.PUSH_NOTIFICATION)
public class PushNotificationService implements NotificationService {
    @Override
    public String sendNotification() {
        return "Sending PUSH Notification";
    }
}
