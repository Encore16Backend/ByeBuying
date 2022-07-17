package com.encore.byebuying.domain.category.dto;

import com.encore.byebuying.domain.category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategorySimpleDTO {
    private Long categoryId;
    private String name;
    private CategorySimpleDTO parentCategory;

    public CategorySimpleDTO(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.parentCategory = setParentCategory(category.getParentCategory());
    }

    private CategorySimpleDTO setParentCategory(Category parentCategory) {
        if (parentCategory == null) {
            return null;
        }
        return new CategorySimpleDTO(parentCategory);
    }
}
