package com.encore.byebuying.domain.basket.dto;

import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateBasketDTO {
    @NotNull
    private Long userId;
    @NotBlank
    @Min(1)
    @Max(100)
    private int count;
}