package com.encore.byebuying.domain.item.dto;

import com.encore.byebuying.domain.item.Item;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor
public class ItemRequestDTO {
    @NotBlank
    private String itemName;
    @NotBlank
    private int price;
    private int count;
    private Long categoryId;

    @Builder
    public Item toEntity() {
        return Item.builder()
          .name(this.itemName)
          .price(this.price)
          .itemImages(new ArrayList<>())
          .build();
    }
}
