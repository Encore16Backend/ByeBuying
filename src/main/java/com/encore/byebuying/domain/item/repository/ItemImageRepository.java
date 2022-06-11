package com.encore.byebuying.domain.item.repository;

import com.encore.byebuying.domain.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    ItemImage findByImgpath(String imgPath);
}
