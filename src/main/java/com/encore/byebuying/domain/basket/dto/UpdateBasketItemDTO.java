package com.encore.byebuying.domain.basket.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateBasketItemDTO {
    @NotNull
    private Long userId;
    @NotBlank
    @Min(1)
    @Max(100)
    private int count;
}