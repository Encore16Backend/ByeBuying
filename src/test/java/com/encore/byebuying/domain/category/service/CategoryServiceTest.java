package com.encore.byebuying.domain.category.service;

import com.encore.byebuying.domain.category.Category;
import com.encore.byebuying.domain.category.dto.CategoryAddDTO;
import com.encore.byebuying.domain.category.dto.CategoryDTO;
import com.encore.byebuying.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void 최상위_카테고리_생성() throws Exception {
        //given
        List<Category> categories = createCategoryEntities();
        given(categoryRepository.findAll())
                .willReturn(categories);

        //when
        CategoryAddDTO root = new CategoryAddDTO("전체", null);
        CategoryDTO categoryRoot = categoryService.findAll();

        System.out.println("categoryRoot.toString() = " + categoryRoot.toString());

        //then
        verify(categoryRepository, atLeastOnce()).findAll();
        // root
        assertThat(categoryRoot.getSubCategories().size()).isEqualTo(2);
        // sub1
        assertThat(categoryRoot.getSubCategories().get(0).getSubCategories().size()).isEqualTo(2);
        // sub2
        assertThat(categoryRoot.getSubCategories().get(1).getSubCategories().size()).isEqualTo(2);
    }

    private List<Category> createCategoryEntities() {
        // ReflectionTestUtils 로 private 값 또한 설정 및 테스트가 가능함

        Category sub1 = new Category("SUB1", 0l);
        Category sub2 = new Category("SUB2", 0l);
        Category sub11 = new Category("SUB1-1", 1l);
        Category sub12 = new Category("SUB1-2", 1l);
        Category sub21 = new Category("SUB2-1", 2l);
        Category sub22 = new Category("SUB2-2", 2l);
        ReflectionTestUtils.setField(sub1, "id", 1l);
        ReflectionTestUtils.setField(sub2, "id", 2l);
        ReflectionTestUtils.setField(sub11, "id", 3l);
        ReflectionTestUtils.setField(sub12, "id", 4l);
        ReflectionTestUtils.setField(sub21, "id", 5l);
        ReflectionTestUtils.setField(sub22, "id", 6l);

        // 주의 : List.of 는 Java version 9 부터 추가된 메소드
        // 프로젝트 jdk version 올리고 pom.xml java version 도 명시해줘야 한다.
        List<Category> categories = List.of(
                sub1, sub2, sub11, sub12, sub21, sub22
        );
        return categories;
    }
}