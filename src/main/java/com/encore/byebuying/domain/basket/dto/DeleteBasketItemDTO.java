package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DeleteBasketItemDTO {
    @NotNull
    @Size(min = 1)
    private List<@NotNull Long> basketItemIds;
}