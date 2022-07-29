package com.encore.byebuying.domain.basket.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BasketItemDeleteDTO {
    @NotBlank
    private List<Long> basketItemIds;
}