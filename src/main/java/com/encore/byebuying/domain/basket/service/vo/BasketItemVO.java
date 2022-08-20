package com.encore.byebuying.domain.basket.service.vo;

import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.ItemImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

@Getter
@ToString
public class BasketItemVO {

    @JsonProperty("basketItemId")
    private Long basketItemId;
    private String itemName;
    private int count;
    private Long categoryId;
    private int price;
    private Collection<ItemImage> itemImages;

    public BasketItemVO() {}

    @QueryProjection
    public BasketItemVO(Long basketItemId, int count, Item item) {
        this.count = count;
        this.basketItemId = basketItemId;
        this.itemName = item.getName();
//        this.categoryId = item.getCategoryId();
        this.price = item.getPrice();
        this.itemImages = item.getItemImages();
    }

}