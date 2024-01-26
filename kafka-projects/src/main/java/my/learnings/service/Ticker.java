package my.learnings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Ticker  {
    @Autowired
    private KafkaTemplate<String, String> template;

    String topic = "test";

    public void send(String message) {
        template.send(topic, message);
    }
}
