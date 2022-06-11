package com.encore.byebuying.domain.item.repository;

import com.encore.byebuying.domain.order.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
