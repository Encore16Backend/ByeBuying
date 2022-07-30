package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.basket.BasketAndItem;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.common.paging.GenericConvertor;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.dto.InquiryResponseDTO;
import com.encore.byebuying.domain.item.ItemImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@Getter
@ToString
public class BasketItemResponseDTO implements GenericConvertor<BasketAndItem, BasketItemResponseDTO> {

    @JsonProperty("basketItemId")
    private Long basketItemId;
    private String itemName;
    private int count;
    private Long categoryId;
    private int price;
    private Collection<ItemImage> itemImages;

    public BasketItemResponseDTO() {}

    public BasketItemResponseDTO(BasketAndItem basketAndItem) {
        // validation, item많이쓰니까 따로 뺴자
        this.count = basketAndItem.getCount();
        this.basketItemId = basketAndItem.getBasketItemId();
        this.itemName = basketAndItem.getItem().getName();
        this.categoryId = basketAndItem.getItem().getCategoryId();
        this.price = basketAndItem.getItem().getPrice();
        this.itemImages = basketAndItem.getItem().getItemImages();
    }
    @Override
    public BasketItemResponseDTO convertor(BasketAndItem basketAndItem) {
        return new BasketItemResponseDTO(basketAndItem);
    }

}

