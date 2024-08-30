package com.rnd.app.strategyasmap.strategy.notification;

import com.rnd.app.strategyasmap.model.notification.NotificationType;
import com.rnd.app.strategyasmap.service.notification.NotificationService;
import org.springframework.stereotype.Service;

@Service(NotificationType.EMAIL)
public class EmailNotificationService implements NotificationService {

    @Override
    public String sendNotification() {
        return "Sending Email Notification";
    }
}
