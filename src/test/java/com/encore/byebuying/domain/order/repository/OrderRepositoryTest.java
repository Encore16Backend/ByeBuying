package com.encore.byebuying.domain.order.repository;

import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.OrderItem;
import com.encore.byebuying.domain.order.dto.OrderResponseDTO;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void 테스트() throws Exception {
        // given
        Address address = new Address("111", "test", "tests");
        User user = User.builder()
                .username("name")
                .password("password")
                .address(address)
                .build();
        User saveUser = userRepository.save(user);

        Item item = Item.createItem("test", 1000);
        Item item2 = Item.createItem("test2", 2000);
        item2.settingStock(20);
        item.settingStock(10);
        itemRepository.save(item);
        itemRepository.save(item2);
        OrderItem orderItem = OrderItem.createOrderItem(item, 1, 800);
        OrderItem orderItem1 = OrderItem.createOrderItem(item2, 1, 100);
        List<OrderItem> list = new ArrayList<>();
        list.add(orderItem);
        list.add(orderItem1);


        Order order = Order.createOrder(saveUser, list, address);

        // when
        Order save = orderRepository.save(order);
        em.flush();
        em.clear();
        Order getOrder = orderRepository.getById(save.getId());

        // then
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(getOrder);
        log.info("orderResponseDTO => {}", orderResponseDTO);
    }
}