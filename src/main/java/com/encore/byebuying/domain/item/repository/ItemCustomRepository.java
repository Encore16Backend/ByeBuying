package com.encore.byebuying.domain.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.encore.byebuying.domain.item.Item;

public interface ItemCustomRepository {
	Page<Item> findBySearch(Pageable pageable, String searchName);
}