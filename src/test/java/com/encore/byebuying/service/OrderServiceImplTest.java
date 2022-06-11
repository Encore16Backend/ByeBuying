//package com.encore.byebuying.service;
//
//import com.encore.byebuying.domain.*;
//import com.encore.byebuying.repo.ItemRepository;
//import com.encore.byebuying.repo.UserRepo;
//import com.encore.byebuying.service.dto.OrderItemInfoServiceDto;
//import com.encore.byebuying.service.dto.OrderItemsServiceOrderDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@Transactional
//class OrderServiceImplTest {
//
//    @Autowired private OrderServiceImpl orderService;
//    @Autowired private UserRepo userRepo;
//    @Autowired private ItemRepository itemRepository;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Test
//    public void orderServiceTest() throws Exception {
//        // given
//        User user = User.builder()
//                .username("test")
//                .address(new Address("test", "test", "test"))
//                .build();
//
//        Item item = Item.builder()
//                .name("testItem")
//                .price(10000)
//                .stockQuantity(100)
//                .build();
//
//        userRepo.save(user);
//        itemRepository.save(item);
//
//        entityManager.flush();
//        entityManager.clear();
//
//        // when
//        // 주문 조건 작성 후 주문
//        List<OrderItemInfoServiceDto> infos = new ArrayList<>();
//        infos.add(new OrderItemInfoServiceDto(item.getId(), 10, 10000));
//        OrderItemsServiceOrderDto orderItemsServiceOrderDto = new OrderItemsServiceOrderDto(user.getId(), infos, user.getAddress());
//
//        Long orderId = orderService.order(orderItemsServiceOrderDto);
//        Order findOrder = orderService.findById(orderId);
//        System.out.println("orderId = " + orderId);
//        System.out.println(findOrder.getId());
//        System.out.println("findOrder.getUser().getUsername() = " + findOrder.getUser().getUsername());
//        System.out.println("findOrder.getItems().size() = " + findOrder.getItems().size());
//
//        // then
//        assertThat(findOrder.getId()).isEqualTo(orderId);
//    }
//
//}