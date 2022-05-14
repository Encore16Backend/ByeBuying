package com.encore.byebuying.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderState {
    RECEIPT("주문접수"),    // 주문 접수
    SHIPPING("배송중"),   // 배송중
    COMP("배송완료"),       // 배송완료
    CANCEL("주문취소");      // 주문 취소 (배송중 취소 불가)

    private final String status;
}
