package com.encore.byebuying.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderType {
    RECEIPT("주문접수"),    // 주문 접수
    SHIPPING("배송중"),    // 배송중
    COMPLETED("배송완료"),       // 배송완료
    CANCEL("주문취소"),     // 주문 취소 (배송중 취소 불가)
    REFUNDED("환불");        // 환불

    private final String status;
}
