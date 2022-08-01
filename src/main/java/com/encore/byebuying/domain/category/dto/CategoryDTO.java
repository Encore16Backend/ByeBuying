package com.encore.byebuying.domain.category.dto;

import com.encore.byebuying.domain.category.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String name;
    private List<CategoryDTO> subCategories;
    public CategoryDTO(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.subCategories = new ArrayList<>();
        if (!(category.getChildCategories().isEmpty())) {
            setSubCategories(category.getChildCategories());
        }
    }

    private void setSubCategories(List<Category> subCategories) {
        List<CategoryDTO> collect = subCategories.stream().map(CategoryDTO::new).collect(Collectors.toList());
        this.subCategories = collect;
    }
}
