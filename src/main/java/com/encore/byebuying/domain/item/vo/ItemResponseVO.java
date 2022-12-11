package com.encore.byebuying.domain.item.vo;

import com.encore.byebuying.domain.item.Item;
import lombok.Data;

@Data
public class ItemResponseVO {
    private Long itemId;
    private String name;
    private int price;

    public ItemResponseVO(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
    }
}
