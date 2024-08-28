package com.rnd.app.strategyasmap.strategy;

import com.rnd.app.strategyasmap.model.NotificationType;
import com.rnd.app.strategyasmap.service.NotificationService;
import org.springframework.stereotype.Service;

@Service(NotificationType.SMS)
public class SMSNotificationService implements NotificationService {
    @Override
    public String sendNotification() {
        return "Sending an SMS notification";
    }
}
