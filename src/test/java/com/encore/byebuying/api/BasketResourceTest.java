package com.encore.byebuying.api;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BasketResourceTest {
//    @Autowired
//    BasketService basketService;
//
//    @Test
//    @Order(1)
//    @Transactional
//    @Rollback(false)
//    public void addBasket() {
//        Basket basket = new Basket(null, "zlel175", 2, 1L, "tmptmptmptmptmptmptmptmptmptmptmptmptmptmp",
//                "tmptmptmptmptmptmptmptmptmptmptmptmptmptmp", 20000);
//
//        Basket addBasket = basketService.saveBasket(basket, "save");
//        Basket getBasket = basketService.getBasketById(addBasket.getId());
//        assertThat(getBasket.getId()).isEqualTo(addBasket.getId());
//        assertThat(getBasket).isEqualTo(addBasket);
//
//    }

//    @Test
//    @Order(2)
//    void getBasketByUsername() {
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<Basket> pages = basketService.getByUsername(pageable, "zlel175");
//        System.out.println(pages);
//    }
//
//    @Test
//    @Order(3)
//    void updateBasket() {
//        Basket basket = new Basket();
//        basket.setId(13L);
//        basket.setBcount(1);
//        Basket result = basketService.saveBasket(basket, "update");
//        assertThat(result.getBcount()).isEqualTo(basket.getBcount());
//    }
//
//    @Test
//    @Order(4)
//    void deleteReview() {
//        basketService.deleteBasket(13L);
//        assertThat(basketService.getBasketById(13L)).isEqualTo(null);
//    }
}