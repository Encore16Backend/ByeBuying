package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DeleteBasketItemDTO {
    @NotBlank
    private List<@NotNull Long> basketItemIds;
}