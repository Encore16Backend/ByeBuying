package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BasketUpdateDTO {
    @NotNull
    private Long basketItemId;
    @NotBlank
    @Min(1)
    private int count;
}