package com.encore.byebuying.domain.item.dto;

import com.encore.byebuying.domain.item.Item;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ItemAddDTO {

  private String itemname;
  private int price;
  private int purchasecnt;
  private int count;
  private double reviewmean;
  private int reviewcount;

  @Builder
  public Item toEntity() {
    return Item.builder()
        .name(this.itemname)
        .price(this.price)
//                .purchasecnt(this.purchasecnt)
//                .count(this.count)
//                .reviewmean(this.reviewmean)
//                .reviewcount(this.reviewcount)
//                .categories(new ArrayList<>())
        .itemImages(new ArrayList<>())
        .build();
  }

}
