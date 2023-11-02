package com.galaxy.novelit.common.kafka;

import java.io.IOException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "testing", groupId = "group-id-test")
    public void consume(String dto) throws IOException {
        System.out.println("receive message : " + dto);
    }
}
