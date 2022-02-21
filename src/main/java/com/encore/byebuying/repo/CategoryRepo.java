package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByCatename(String catename);
    Category findByCateid(int cateid);
}
