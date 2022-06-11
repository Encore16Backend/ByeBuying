package com.encore.byebuying.domain.item.repository;

import com.encore.byebuying.domain.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>{
    Page<Item> findAll(Pageable pageable);

    Item findByName(String name);
    // Best Item

    @Query("select i from Item i where i.id in :ids")
    List<Item> findByIds(@Param("ids") List<Long> itemIds);

    @Query("select a from Item a where a.id in :id")
    List<Item> findImageRetrieval(Long[] id);
}
