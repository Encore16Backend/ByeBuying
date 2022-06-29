package com.encore.byebuying.domain.category.repository;

import com.encore.byebuying.domain.category.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 생성 테스트")
    @Test
    public void categoryTest() throws Exception {
        // given
        Category category = Category.createCategory()
                .name("전체")
                .parentId(null)
                .build();

        // when
        Category save = categoryRepository.save(category);

        // then
        assertThat(save.getName()).isEqualTo("전체");
        assertThat(save.getId()).isNotNull();
    }

    @DisplayName("카테고리 엔티티 자기참조 테스트")
    @Test
    public void categoryInCategoryTest() throws Exception {
        // given
        Category category = Category.createCategory()
                .name("전체")
                .parentId(null)
                .build();
        Category save = categoryRepository.save(category);

        // when
        Category inCategory = Category.createCategory()
                .name("패션")
                .parentId(save.getId())
                .build();
        Category saveInCategory = categoryRepository.save(inCategory);

        // then
        assertThat(saveInCategory.getId()).isNotNull();
        assertThat(saveInCategory.getParentId()).isEqualTo(save.getId());
        assertThat(saveInCategory.getName()).isEqualTo("패션");
    }
}