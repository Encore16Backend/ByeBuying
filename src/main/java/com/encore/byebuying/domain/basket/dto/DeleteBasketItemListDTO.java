package com.encore.byebuying.domain.basket.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DeleteBasketItemListDTO {
    @NotNull
    @Size(min = 1)
    private List<Long> basketItemIds;
}