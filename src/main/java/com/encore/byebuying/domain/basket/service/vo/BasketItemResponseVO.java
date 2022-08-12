package com.encore.byebuying.domain.basket.service.vo;

import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.service.vo.InquiryResponseVO;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.ItemImage;
import com.encore.byebuying.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

@Getter
@ToString
public class BasketItemResponseVO {

    @JsonProperty("basketItemId")
    private Long basketItemId;
    private String itemName;
    private int count;
    private Long categoryId;
    private int price;
    private Collection<ItemImage> itemImages;

    public BasketItemResponseVO() {}

    @QueryProjection
    public BasketItemResponseVO(Long basketItemId, int count, Item item) {
        this.count = count;
        this.basketItemId = basketItemId;
        this.itemName = item.getName();
        this.categoryId = item.getCategoryId();
        this.price = item.getPrice();
        this.itemImages = item.getItemImages();
    }

}

