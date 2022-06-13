//package com.encore.byebuying.domain.item.repository;
//
////import com.encore.byebuying.domain.item.QItem;
////import com.encore.byebuying.domain.order.QCategory;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.StringTokenizer;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//
//import com.encore.byebuying.domain.item.Item;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.jpa.JPQLQuery;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class ItemImplRepository extends QuerydslRepositorySupport implements ItemCustomRepository {
////	private QItem item = QItem.item;
////	private QCategory category = QCategory.category;
//	private CategoryRepository categoryRepository;
//	private final Set<String> categories=
//    		new HashSet<String>(Arrays.asList(new String[] {"상의","바지","스커트","아우터","반팔","긴팔","셔츠","반바지","슬랙스","데님팬츠","미니스커트","롱스커트","롱패딩","숏패딩","트렌치코트","코트"}));
//	public ItemImplRepository() {
//		super(Item.class);
//	}
//
//	@Override
//	public Page<Item> findBySearch(Pageable pageable,String searchName) {
////		JPQLQuery<Item> query = from(item).select(item);
//		BooleanBuilder bb = new BooleanBuilder();
//
//    	StringTokenizer st = new StringTokenizer(searchName);
//    	while(st.hasMoreTokens()) {
//    		String keyword = st.nextToken();
////			System.out.println(keyword);
//    		if(categories.contains(keyword)) {
//    			log.info("search by category : {}",keyword);
////				if (keyword.equals("트렌치코트")) keyword = "트렌치 코트";
////    			query.where(item.get.contains(from(category).where(category.catename.eq(keyword))));
////    		}else {
////    			bb.or(item.itemname.like("%"+keyword+"%"));
//    		}
//    	}
////    	query = query.where(bb);
////		final List<Item> result = getQuerydsl().applyPagination(pageable, query).fetch();//query.fetchResults();
//		//final List<Item> result = getQuerydsl().applyPagination(pageable, query)
////		return new PageImpl<Item>(result,pageable,query.fetchCount());
//	}
//}
