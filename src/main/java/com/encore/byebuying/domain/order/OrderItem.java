package com.encore.byebuying.domain.order;

import com.encore.byebuying.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    // 상품가격과는 별도의 로직으로 할인등이 적용된 주문 가격
    // 지금은 기능이 없지만 나중에 추가될 수 있음
    // 상품 가격 x 주문 당시 가격 (회원별 가격이 다를 수도 쿠폰이 적용된 상품일 수도 등등)
    private int orderPrice;

    public static OrderItem createOrderItem(Item item, int count, int orderPrice) {
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .count(count)
                .orderPrice(orderPrice)
                .build();

        item.removeStock(count);
        return orderItem;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
