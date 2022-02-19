package com.encore.byebuying.api;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.repo.CategoryRepo;
import com.encore.byebuying.repo.ImageRepo;
import com.encore.byebuying.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class ItemResource {
    private final ItemService itemService;
    private final CategoryRepo categoryRepo;
    private final ImageRepo imageRepo;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItems() {
        List<Item> item = itemService.getItems();
        return ResponseEntity.ok().body(item);
    }
}
