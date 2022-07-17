package com.encore.byebuying.domain.category.service;

import com.encore.byebuying.domain.category.Category;
import com.encore.byebuying.domain.category.dto.CategoryAddDTO;
import com.encore.byebuying.domain.category.dto.CategoryDTO;
import com.encore.byebuying.domain.category.dto.CategorySimpleDTO;
import com.encore.byebuying.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /** 전체 카테고리 반환*/
    public CategoryDTO findAll() {
        return new CategoryDTO(
                categoryRepository.findByParentCategoryIsNull()
                        .orElseThrow(() -> new NullPointerException("최상위 카테고리가 없습니다. "))
        );
    }
    
    /** 단일 카테고리 반환 */
    public CategorySimpleDTO findCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("not found category"));
        CategorySimpleDTO categorySimpleDTO = new CategorySimpleDTO(category);
        return categorySimpleDTO;
    }


    /** 카테고리 생성 */
    @Transactional
    public CategoryDTO createCategory(CategoryAddDTO request) {
        
        // 여기서 Default 로 최상위 카테고리의 하위 카테고리로 줄 수도 있음
        Category parent = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new NullPointerException("최상위 카테고리 외에는 부모 카테고리가 필요합니다."));

        Category category = Category.createCategory(request.getName(), parent);

        return new CategoryDTO(categoryRepository.save(category));
    }
}
