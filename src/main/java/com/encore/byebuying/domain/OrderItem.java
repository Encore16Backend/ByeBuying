package com.encore.byebuying.domain;

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
    @JoinColumn(name = "order_history_id")
    private OrderHistory orderHistory;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    // 상품가격과는 별도의 로직으로 할인등이 적용된 주문 가격
    // 지금은 기능이 없지만 나중에 추가될 수 있음
    private int orderPrice;
}
