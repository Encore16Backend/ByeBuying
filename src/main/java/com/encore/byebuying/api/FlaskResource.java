package com.encore.byebuying.api;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.service.ItemService;
import com.encore.byebuying.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flask")
public class FlaskResource {
    private final WebClientService webClientService;
    private final ItemService itemService;

    @GetMapping("/hello")
    public Flux<String> getHello() {
        return webClientService.getHello();
    }

    @PostMapping("/retrieval")
    public ResponseEntity<List<Item>> retrieval(
            @RequestBody MultipartFile file) throws IOException {
        String[] res;
        try {
             res = Objects.requireNonNull(webClientService.postImage(file)
                    .share().block()).split(",");
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(null);
        }

        Long[] ids = new Long[res.length];
        for(int i=0; i<res.length; i++){
            ids[i] = Long.parseLong(res[i]);
        }
        System.out.println(Arrays.toString(ids));
        List<Item> items = itemService.getItemRetrieval(ids);

        return ResponseEntity.ok().body(items);
    }
}
