package com.encore.byebuying.domain.basket.repository;
import com.encore.byebuying.domain.basket.dto.BasketItemSearchDTO;
import com.encore.byebuying.domain.basket.service.vo.BasketItemResponseVO;
import org.springframework.data.domain.Page;

public interface CustomBasketItemRepository{

    Page<BasketItemResponseVO> getByUser(BasketItemSearchDTO basketItemSearchDTO, Long baksetID);

}
