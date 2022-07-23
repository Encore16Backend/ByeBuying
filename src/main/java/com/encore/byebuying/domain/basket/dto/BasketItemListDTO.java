package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.basket.BasketAndItem;
import com.encore.byebuying.domain.basket.BasketItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
public class BasketItemListDTO {

    private List<BasketItemResponseDTO> basketItems;

    public BasketItemListDTO(List<BasketAndItem> BasketAndItem) {
        this.basketItems = BasketAndItem.stream()
                .map(BasketItemResponseDTO::new).collect(Collectors.toList());
    }
}

