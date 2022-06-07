package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Basket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepo extends JpaRepository<Basket, Long> {
//    Page<Basket> findByUsername(Pageable pageable, Long user_id);
//    void deleteById(Long id);
//    void deleteByItemidAndUsername(Long itemid, String username);
//    void deleteAllByUsername(String username);
//    Basket findBasketByUsernameAndItemid(String username, Long itemid);
//    Basket findBasketById(Long id);

}
