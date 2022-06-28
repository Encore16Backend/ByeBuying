package com.encore.byebuying.domain.category.service;

import com.encore.byebuying.domain.category.Category;
import com.encore.byebuying.domain.category.dto.CategoryAddDTO;
import com.encore.byebuying.domain.category.dto.CategoryDTO;
import com.encore.byebuying.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /* Todo : JPA 로 자기 참조 가능한지 알아보기 */
    public void findAll() {
        Map<Long, List<CategoryDTO>> groupingByParentId =
                categoryRepository.findAll().stream().map(CategoryDTO::new)
                        .collect(Collectors.groupingBy(CategoryDTO::getParentId));

        CategoryDTO rootCategory = new CategoryDTO();
        rootCategory.setCategoryId(0L);
        rootCategory.setName("전체");
        rootCategory.setParentId(null);

    }

    /** 카테고리 생성 */
    @Transactional
    public CategoryDTO createCategory(CategoryAddDTO request) {
        Category category = Category.createCategory()
                .name(request.getName())
                .parentId(request.getParentId())
                .build();
        return new CategoryDTO(categoryRepository.save(category));
    }

    private void initSubCategories(CategoryDTO parent, Map<Long, List<CategoryDTO>> groupingByParentId) {
        List<CategoryDTO> subCategories = groupingByParentId.get(parent.getParentId());

        if (subCategories == null) {
            return;
        }

        parent.setSubCategories(subCategories);

        subCategories.stream()
                .forEach(subCategory -> initSubCategories(subCategory, groupingByParentId));
    }


}
