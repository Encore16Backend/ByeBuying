package com.encore.byebuying.repo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.encore.byebuying.domain.Category;
import com.encore.byebuying.domain.Item;
import com.encore.byebuying.domain.QCategory;
import com.encore.byebuying.domain.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemRepoImpl extends QuerydslRepositorySupport implements ItemRepoCustom{
	private QItem item = QItem.item;
	private QCategory category = QCategory.category;
	private CategoryRepo categoryRepo;
	private final Set<String> categories=
    		new HashSet<String>(Arrays.asList(new String[] {"상의","바지","스커트","아우터","반팔","긴팔","셔츠","반바지","슬랙스","데님팬츠","미니스커트","롱스커트","롱패딩","숏패딩","트렌치코트","코트"}));
	public ItemRepoImpl() {
		super(Item.class);
	}

	@Override
	public Page<Item> findBySearch(Pageable pageable,String searchName) {
		JPQLQuery<Item> query = from(item).select(item);
		BooleanBuilder bb = new BooleanBuilder();
		
    	StringTokenizer st = new StringTokenizer(searchName);
    	while(st.hasMoreTokens()) {
    		String keyword = st.nextToken();
//			System.out.println(keyword);
    		if(categories.contains(keyword)) {
    			log.info("search by category : {}",keyword);
    			query.where(item.categories.contains(from(category).where(category.catename.eq(keyword))));
    		}else {
    			bb.or(item.itemname.like("%"+keyword+"%"));
    		}
    	}
    	query = query.where(bb);
		final List<Item> result = getQuerydsl().applyPagination(pageable, query).fetch();//query.fetchResults();
		//final List<Item> result = getQuerydsl().applyPagination(pageable, query)
		return new PageImpl<Item>(result,pageable,query.fetchCount());
	}
}
