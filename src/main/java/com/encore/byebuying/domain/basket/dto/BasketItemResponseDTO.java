package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.common.paging.GenericConvertor;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.dto.InquiryResponseDTO;
import com.encore.byebuying.domain.item.ItemImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

@Getter
@ToString
public class BasketItemResponseDTO implements GenericConvertor<BasketItem, BasketItemResponseDTO> {

    @JsonProperty("basketItemId")
    private Long basketItemId;
    private String itemName;
    private int count;
    private Long categoryId;
    private int price;
    private Collection<ItemImage> itemImages;

    public BasketItemResponseDTO() {}

    public BasketItemResponseDTO(BasketItem basketItem) {
        this.count = basketItem.getCount();
        this.basketItemId = basketItem.getId();
        this.itemName = basketItem.getItem().getName();
        this.categoryId = basketItem.getItem().getCategoryId();
        this.price = basketItem.getItem().getPrice();
        this.itemImages = basketItem.getItem().getItemImages();
    }

    @Override
    public BasketItemResponseDTO convertor(BasketItem basketItem) {
        return new BasketItemResponseDTO(basketItem);
    }


}

