package com.encore.byebuying.api;

import com.encore.byebuying.domain.Item;
import com.encore.byebuying.service.ItemService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemResourceTest {
    @Autowired
    ItemService itemService;

    @Test
    @Order(1)
    public void start(){
        itemService.getItems();
    }

    @Test
    @Order(3)
    public void getItembyItemname() { // 43ms <- 1 /
        itemService.getItemByItemname("여성 트위드 배색 스커트 VIOLET");
    }

    @Test
    @Order(2)
    public void getItembyItemid() { // 16ms <- 2 /
        itemService.getItemByItemid(641L);
    }

}