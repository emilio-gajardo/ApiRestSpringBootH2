package com.api.jpayh2.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {
    
    private static final Logger log = LoggerFactory.getLogger(MyListener.class);

    @KafkaListener(topics = "mi-topic", groupId = "MyConsumerGroup")
    public void listen(String message){
        log.info("Code to post the message in the audit api: {}", message);
        
        // Simulación de envío del post a la api que domara en responder 5 segs
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
