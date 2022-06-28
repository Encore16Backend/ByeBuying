package com.encore.byebuying.domain.category.dto;

import com.encore.byebuying.domain.category.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDTO {

    private Long categoryId;
    private String name;
    private Long parentId;
    private List<CategoryDTO> subCategories;

    @Builder(builderMethodName = "createCategory")
    public CategoryDTO(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.parentId = category.getParentId();
        this.subCategories = new ArrayList<>();
    }

    public void setSubCategories(List<CategoryDTO> subCategories) {
        this.subCategories = subCategories;
    }
}
