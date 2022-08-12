package com.encore.byebuying.domain.basket.service.vo;

import com.encore.byebuying.domain.basket.Basket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemRequestVO {

    Long basket_id;

    public static BasketItemRequestVO valueOf(Basket basket) {
        BasketItemRequestVO vo = new BasketItemRequestVO();
        vo.setBasket_id(basket.getId());
        return vo;
    }

}
