package com.encore.byebuying.domain.category.service;

import com.encore.byebuying.domain.category.Category;
import com.encore.byebuying.domain.category.dto.CategoryDTO;
import com.encore.byebuying.domain.category.dto.CategorySimpleDTO;
import com.encore.byebuying.domain.category.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;
    
    @Test
    public void 단일_카테고리_생성() throws Exception {
        // given
        List<Category> categoryEntities = createCategoryEntities();
        Category category = categoryEntities.get(categoryEntities.size() - 1);

        when(categoryRepository.findById(category.getId()))
                .thenReturn(Optional.ofNullable(category));

        // when
        CategorySimpleDTO categorySimpleDTO = categoryService.findCategory(category.getId());
        log.info("category dto : {}", categorySimpleDTO);

        // then
        assertThat(categorySimpleDTO.getParentCategory().getName()).isEqualTo("SUB2");
    }

    @Test
    public void 전체_카테고리_테스트() throws Exception {
        //given
        List<Category> categories = createCategoryEntities();

        when(categoryRepository.findByParentCategoryIsNull())
                .thenReturn(Optional.ofNullable(categories.get(0)));

        //when
        CategoryDTO all = categoryService.findAll();

        log.info("{}", all.toString());

        //then
    }

    private List<Category> createCategoryEntities() {
        // ReflectionTestUtils 로 private 값 또한 설정 및 테스트가 가능함
        Category root = Category.createCategory("root", null);
        Category sub1 = Category.createCategory("SUB1", root);
        Category sub2 = Category.createCategory("SUB2", root);
        Category sub11 = Category.createCategory("SUB1-1", sub1);
        Category sub12 = Category.createCategory("SUB1-2", sub1);
        Category sub21 = Category.createCategory("SUB2-1", sub2);
        Category sub22 = Category.createCategory("SUB2-2", sub2);
        ReflectionTestUtils.setField(root, "id", 0L);
        ReflectionTestUtils.setField(sub1, "id", 1l);
        ReflectionTestUtils.setField(sub2, "id", 2l);
        ReflectionTestUtils.setField(sub11, "id", 3l);
        ReflectionTestUtils.setField(sub12, "id", 4l);
        ReflectionTestUtils.setField(sub21, "id", 5l);
        ReflectionTestUtils.setField(sub22, "id", 6l);

        // 주의 : List.of 는 Java version 9 부터 추가된 메소드
        // 프로젝트 jdk version 올리고 pom.xml java version 도 명시해줘야 한다.
        List<Category> categories = List.of(
                root, sub1, sub2, sub11, sub12, sub21, sub22
        );
        return categories;
    }
}