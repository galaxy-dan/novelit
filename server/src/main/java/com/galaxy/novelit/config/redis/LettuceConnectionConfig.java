package com.galaxy.novelit.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class LettuceConnectionConfig {

    /*@Value("${spring.data.redis.cluster.nodes}")
    private String clusterNodes;

    @Value("${spring.data.redis.cluster.max-redirects}")
    private int maxRedirects;

    @Value("${spring.data.redis.password}")
    private String password;

    private final SSERepository sseRepository;

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModules(new JavaTimeModule(), new Jdk8Module());
    }*/
}
