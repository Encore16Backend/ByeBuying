package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.dto.InquiryDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
public class BasketItemListDTO {
    private List<BasketItemDTO> basketItems;

    public BasketItemListDTO(List<BasketItem> basketItems) {
        this.basketItems = basketItems.stream()
                .map(BasketItemDTO::new).collect(Collectors.toList());
    }
}

