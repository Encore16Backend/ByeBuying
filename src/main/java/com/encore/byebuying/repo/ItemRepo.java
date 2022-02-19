package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {
    Item findByItemname(String itemname);
//    List<Item> findByCategoriesOrderByReviewmeanDesc(List<Category> categories);
}
