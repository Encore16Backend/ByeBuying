package com.encore.byebuying.domain.basket;

import com.encore.byebuying.domain.item.Item;


public interface BasketAndItem {
     // Projection
     Item getItem();
     Long getBasketItemId();
     int getCount();
}
