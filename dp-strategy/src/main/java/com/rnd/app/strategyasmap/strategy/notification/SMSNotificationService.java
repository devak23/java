package com.rnd.app.strategyasmap.strategy.notification;

import com.rnd.app.strategyasmap.model.notification.NotificationType;
import com.rnd.app.strategyasmap.service.notification.NotificationService;
import org.springframework.stereotype.Service;

@Service(NotificationType.SMS)
public class SMSNotificationService implements NotificationService {
    @Override
    public String sendNotification() {
        return "Sending an SMS notification";
    }
}
