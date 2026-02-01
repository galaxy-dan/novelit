package com.galaxy.novelit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NovelitApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelitApplication.class, args);
    }

//    @Bean
//    public NewTopics topics456() {
//        return new NewTopics(
//            TopicBuilder.name("article")
//                .partitions(3)
//                .replicas(1)
//                .build(),
//            TopicBuilder.name("comment")
//                .partitions(3)
//                .replicas(1)
//                .build());
//    }
}
