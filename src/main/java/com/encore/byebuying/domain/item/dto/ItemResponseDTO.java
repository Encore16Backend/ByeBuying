package com.encore.byebuying.domain.item.dto;

import com.encore.byebuying.domain.item.Item;
import lombok.Data;

@Data
public class ItemResponseDTO {
    private Long itemId;
    private String name;
    private int price;

    public ItemResponseDTO(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
    }
}
