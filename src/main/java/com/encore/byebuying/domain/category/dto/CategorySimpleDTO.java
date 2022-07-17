package com.encore.byebuying.domain.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategorySimpleDTO {

    private Long categoryId;
    private String name;
    private Long parentId;
}
