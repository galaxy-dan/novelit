package com.galaxy.novelit.common.kafka;

import com.galaxy.novelit.directory.dto.request.FileWorkReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {

    @Autowired
    private KafkaProducer kafkaProducerService;

    @PostMapping(value = "/sendMessage")
    public void sendMessage(FileWorkReqDTO dto) {
        kafkaProducerService.sendMessage(dto);
    }

}
