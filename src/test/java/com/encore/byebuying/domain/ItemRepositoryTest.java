package com.encore.byebuying.domain;

import com.encore.byebuying.repo.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 아이템_생성_테스트() throws Exception {
        // given
        Item item = Item.builder()
                .name("상품")
                .price(1000)
                .stockQuantity(10)
                .build();

        // when
        Item saveItem = itemRepository.save(item);

        // then
        assertThat(saveItem).isNotNull();
        assertThat(saveItem.getId()).isEqualTo(item.getId());
        assertThat(saveItem.getPrice()).isEqualTo(1000);
        assertThat(saveItem.getStockQuantity()).isEqualTo(10);
    }

    @Test
    public void 아이템_조회_테스트() throws Exception {
        // given
        Item itemA = Item.builder()
                .name("상품1")
                .price(1000)
                .stockQuantity(10)
                .build();

        itemRepository.save(itemA);

        // when
        Item findItem = itemRepository.findById(itemA.getId()).orElseGet(() -> Item.builder().build());

        // then
        assertThat(findItem.getId()).isEqualTo(itemA.getId());
        assertThat(findItem.getName()).isEqualTo("상품1");
        assertThat(findItem.getPrice()).isEqualTo(1000);
        assertThat(findItem.getStockQuantity()).isEqualTo(10);
    }

}
