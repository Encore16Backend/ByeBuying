package com.encore.byebuying.api;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import javax.swing.plaf.synth.SynthEditorPaneUI;
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
    public ResponseEntity<Flux<String>> retrieval(
            @RequestBody MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(webClientService.postImage(file));
    }

}
