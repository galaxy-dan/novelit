package com.galaxy.novelit.common.kafka;

import com.galaxy.novelit.directory.dto.request.FileWorkReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(FileWorkReqDTO dto) {
        System.out.println("send message : " +  dto);
        this.kafkaTemplate.send("testing", dto.toString());
    }
}
