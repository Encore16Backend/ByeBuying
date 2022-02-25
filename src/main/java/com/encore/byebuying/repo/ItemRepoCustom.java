package com.encore.byebuying.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.encore.byebuying.domain.Item;

public interface ItemRepoCustom{
	Page<Item> findBySearch(Pageable pageable, String searchName);
}