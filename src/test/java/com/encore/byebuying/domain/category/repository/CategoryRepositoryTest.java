package com.encore.byebuying.domain.category.repository;

import com.encore.byebuying.domain.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void init() {
        Category allCate = Category.createCategory()
                .name("name")
                .build();

        categoryRepository.save(allCate);
    }
}