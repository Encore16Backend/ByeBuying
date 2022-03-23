package com.encore.byebuying.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service @Slf4j @RequiredArgsConstructor
public class WebClientService {
    private final WebClient webClient;

    // Get 방식 요청 - 테스트
    public Flux<String> getHello(){
        return webClient.get()
                .uri("/")
                .retrieve()// body를 가져옴
                .bodyToFlux(String.class); // Mono 객체는 0-1개의 결과를 처리, Flux 객체는 0-N개의 결과를 처리
    }

    public Mono<String> postImage(MultipartFile file) throws IOException {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getBytes())
                .header("Content-Disposition", "form-data; name=file; filename="+file.getName());

        return webClient.post()
                .uri("/image")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class);
    }

    @Async // 비동기 사용
    public Future<Mono<String>> recommendItem(String username) {
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        return CompletableFuture.completedFuture(webClient.post()
                .uri("/recommend")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class));
    }

    @Async // 비동기 사용
    public void newUser(String username) {
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        webClient.post()
                .uri("/user")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(res -> {
                    if (res == null || res.equals("FAIL"))
                        log.error("Flask Communication Error");
                    else
                        log.info("Flask Communication Success");
                });
    }

    @Async // 비동기 사용
    public void checkPurchaseHistory(String username, Long[] itemids) {
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("itemids", itemids);
        webClient.post()
                .uri("/order")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(res -> {
                    if (res == null || res.equals("FAIL"))
                        log.error("Flask Communication Error");
                    else
                        log.info("Flask Communication Success");
                });
    }
}
