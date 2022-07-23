package com.encore.byebuying.domain.basket;

import com.encore.byebuying.domain.item.Item;


public interface BasketAndItem {

//    basketitem0_.id as id1_1_0_,
//    item1_.item_id as item_id1_4_1_,
//    basketitem0_.created_at as created_2_1_0_,
//    basketitem0_.modified_at as modified3_1_0_,
//    basketitem0_.basket_id as basket_i5_1_0_,
//    basketitem0_.count as count4_1_0_,
//    basketitem0_.item_id as item_id6_1_0_,
//    basketitem0_.user_id as user_id7_1_0_,
//    item1_.created_at as created_2_4_1_,
//    item1_.modified_at as modified3_4_1_,
//    item1_.category_id as category4_4_1_,
//    item1_.name as name5_4_1_,
//    item1_.price as price6_4_1_,
//    item1_.stock_quantity as stock_qu7_4_1_

    BasketItem getBasketItem();
    Item getItem();

}
