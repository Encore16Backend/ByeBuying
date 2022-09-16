package com.encore.byebuying.domain.order.repository;

import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.common.paging.PagingRequest;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.order.OrderItem;
import com.encore.byebuying.domain.order.vo.OrderListVO;
import com.encore.byebuying.domain.order.vo.OrderResponseVO;
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
        List<OrderItem> list = List.of(orderItem, orderItem1);

        OrderItem orderItem2 = OrderItem.createOrderItem(item, 1, 800);
        OrderItem orderItem3 = OrderItem.createOrderItem(item2, 1, 100);
        List<OrderItem> list2 = List.of(orderItem2, orderItem3);

        OrderItem orderItem4 = OrderItem.createOrderItem(item, 1, 800);
        OrderItem orderItem5 = OrderItem.createOrderItem(item2, 1, 100);
        List<OrderItem> list3 = List.of(orderItem4, orderItem5);


        Order order = Order.createOrder(saveUser, list, address);
        Order order2 = Order.createOrder(saveUser, list2, address);
        Order order3 = Order.createOrder(saveUser, list3, address);

        // when
        Order save = orderRepository.save(order);
        Order save2 = orderRepository.save(order2);
        Order save3 = orderRepository.save(order3);
        log.info("save2 :: {}", save2.getId());
//        Order getOrder = orderRepository.getById(save.getId());
        em.flush();
        em.clear();

        PagingRequest pagingRequest = new PagingRequest();
        pagingRequest.setSize(2);
        Pageable pageable = pagingRequest.getPageRequest();

        PagingRequest pagingRequest2 = new PagingRequest();
        pagingRequest.setSize(2);
        pagingRequest.setPageNumber(1);
        Pageable pageable2 = pagingRequest.getPageRequest();

        log.info("===============");
        Page<OrderListVO> pageOrderList1 =
                orderRepositoryCustom.findByCreatedAtBetweenAndUser(pageable, null, null, saveUser.getId());
        Page<OrderListVO> pageOrderList2 =
                orderRepositoryCustom.findByCreatedAtBetweenAndUser(pageable2, null, null, saveUser.getId());

        // then
//        OrderResponseVO orderResponseVO = new OrderResponseVO(getOrder);
//        log.info("orderResponseDTO => {}", orderResponseVO);
        log.info("OrderListVO 1page => {}", pageOrderList1.getContent());
        log.info("OrderListVO 2page => {}", pageOrderList2.getContent());
        // TODO: 2022-09-16 예상 total page : 2, 결과 total page : 1 확인해야징
        log.info("Total pages => {}", pageOrderList1.getTotalPages());
    }
}