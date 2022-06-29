package com.encore.byebuying.domain.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CategoryAddDTO {
    @NotNull
    private String name;
    private Long parentId;

    public CategoryAddDTO(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
