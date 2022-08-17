package com.encore.byebuying.domain.item.dto;

import com.encore.byebuying.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.ArrayList;

@Getter @Setter @NoArgsConstructor
public class ItemRequestDTO {
    @NotBlank
    private String itemName;
    @PositiveOrZero
    private BigDecimal price;
    @PositiveOrZero
    private BigDecimal count;
    @NotNull
    private Long categoryId;
    @Builder
    public Item toEntity() {
        return Item.builder()
          .name(this.itemName)
          .price(this.price.intValue())
          .itemImages(new ArrayList<>())
          .build();
    }
}
