package com.encore.byebuying.domain.category.repository;

import com.encore.byebuying.domain.category.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @EntityGraph(attributePaths = "parentCategory")
    Optional<Category> findByParentCategoryIsNull();
}
