package com.encore.byebuying.domain.category.repository;

import com.encore.byebuying.domain.category.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 생성 테스트")
    @Test
    public void categoryTest() throws Exception {
        // given
        Category category = Category.createCategory("전체", null);

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
        Category allCategory = Category.createCategory("전체", null);
        Category save = categoryRepository.save(allCategory);

        // when
        Category fashionCategory = Category.createCategory("패션", allCategory);
        Category saveInCategory = categoryRepository.save(fashionCategory);

        log.info("result = {}", allCategory.getChildCategories());

        // then
        assertThat(saveInCategory.getId()).isNotNull();
        assertThat(saveInCategory.getParentCategory().getId()).isEqualTo(save.getId());
        assertThat(saveInCategory.getName()).isEqualTo("패션");
    }
}