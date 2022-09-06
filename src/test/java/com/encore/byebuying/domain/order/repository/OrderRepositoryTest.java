package com.encore.byebuying.domain.order.repository;

import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.common.paging.PagingRequest;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.OrderItem;
import com.encore.byebuying.domain.order.dto.OrderListVO;
import com.encore.byebuying.domain.order.dto.OrderResponseVO;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.UpdateUserDTO;
import com.encore.byebuying.domain.user.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderRepositoryCustom orderRepositoryCustom;

    @Autowired
    private ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void 테스트() throws Exception {
        // given
        Address address = new Address("111", "test", "tests");
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(null,"name", "password", "test@test.com");
        User user = User.updateUser()
            .dto(updateUserDTO)
            .provider(ProviderType.LOCAL)
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

        Pageable pageRequest = new PagingRequest().getPageRequest();

        Page<OrderListVO> name = orderRepositoryCustom.findByUsername(pageRequest, "name");

        // then
        OrderResponseVO orderResponseVO = new OrderResponseVO(getOrder);
        log.info("orderResponseDTO => {}", orderResponseVO);
        log.info("OrderListVO => {}", name.get().collect(Collectors.toList()));

    }
}