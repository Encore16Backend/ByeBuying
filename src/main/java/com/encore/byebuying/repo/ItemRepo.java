package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Long> {
    Item findByItemname(String itemname);
}
