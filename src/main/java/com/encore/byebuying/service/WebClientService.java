package com.encore.byebuying.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service @Slf4j @RequiredArgsConstructor
public class WebClientService {
    private final WebClient webClient;

    // Get 방식 요청 - 테스트
    public Flux<String> getHello(){
        return webClient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(String.class);
    }
}
