package com.encore.byebuying.service;

import com.encore.byebuying.domain.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

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

    public Flux<String> postImage(MultipartFile file) throws IOException {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getBytes())
                .header("Content-Disposition", "form-data; name=file; filename="+file.getName());
        MultiValueMap<String, HttpEntity<?>> multiValueMap = builder.build();
        return webClient.post()
                .uri("/image")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToFlux(String.class);
    }
}
