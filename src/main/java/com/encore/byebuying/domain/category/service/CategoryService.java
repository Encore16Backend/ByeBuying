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

    /* Todo : JPA 로 자기 참조 가능한지 알아보기 끝 */
    // Todo : 전체 카테고리 조회(원하는 반환 결과 만들기), 아이템 카테고리

    public CategoryDTO findAll() {
        List<CategoryDTO> collect = categoryRepository.findAll()
                .stream().map(CategoryDTO::new).collect(Collectors.toList());


        CategoryDTO rootCategory = new CategoryDTO();
        rootCategory.setCategoryId(0L);
        rootCategory.setName("전체");


        return rootCategory;
    }
//
//    /** 카테고리 생성 */
//    @Transactional
//    public CategoryDTO createCategory(CategoryAddDTO request) {
//        Category category = Category.createCategory()
//                .name(request.getName())
//                .parentId(request.getParentId())
//                .build();
//
//        return new CategoryDTO(categoryRepository.save(category));
//    }
//
//    /** 루트부터 모든 카테고리 리스트 초기화 */
//    private void initSubCategories(CategoryDTO parent, Map<Long, List<CategoryDTO>> groupingByParentId) {
//        List<CategoryDTO> subCategories = groupingByParentId.get(parent.getCategoryId());
//        if (subCategories == null) {
//            return;
//        }
//
//        parent.setSubCategories(subCategories);
//
//        subCategories.stream()
//                .forEach(subCategory -> initSubCategories(subCategory, groupingByParentId));
//    }


}
