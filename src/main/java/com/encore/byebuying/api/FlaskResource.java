package com.encore.byebuying.api;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flask")
public class FlaskResource {
    private final WebClientService webClientService;

    @GetMapping("/hello")
    public Flux<String> getHello() {
        return webClientService.getHello();
    }

    @PostMapping("/retrieval")
    public ResponseEntity<Item> retrieval(
            @RequestBody MultipartFile file) throws IOException {
        System.out.println(Arrays.toString(file.getBytes()));
        System.out.println(file.getName()+" "+file.getOriginalFilename()+" "+file.getContentType());
        return ResponseEntity.ok().body(null);
    }

}
