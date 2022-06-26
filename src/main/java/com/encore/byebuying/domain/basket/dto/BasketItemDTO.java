package com.encore.byebuying.domain.basket.dto;

import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.item.ItemImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

@Getter
@ToString
public class BasketItemDTO {

    @JsonProperty("basketItemId")
    private Long basketItem_id;
    private String itemName;
    private int count;
    private Long categoryId;
    private int price;
    private Collection<ItemImage> itemImages;


    public BasketItemDTO(BasketItem basketItem) {
        this.count = basketItem.getCount();
        this.basketItem_id = basketItem.getId();
        this.itemName = basketItem.getItem().getName();
        this.categoryId = basketItem.getItem().getCategoryId();
        this.price = basketItem.getItem().getPrice();
        this.itemImages = basketItem.getItem().getItemImages();
    }
}

