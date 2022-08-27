package com.encore.byebuying.domain.basket.service.vo;

import com.encore.byebuying.domain.basket.BasketItem;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.ItemImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
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

    public static BasketItemVO valueOf(BasketItem basketItem){
        BasketItemVO basketItemVO = new BasketItemVO();

        basketItemVO.setBasketItemId(basketItem.getId());
        basketItemVO.setCount(basketItem.getCount());

        Item item = basketItem.getItem();
        basketItemVO.setItemName(item.getName());
        basketItemVO.setPrice(item.getPrice());
        basketItemVO.setItemImages(item.getItemImages());

        return basketItemVO;
    }

}