package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Basket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepo extends JpaRepository<Basket, Long> {
    Page<Basket> findByUsername(Pageable pageable, String username);
    void deleteById(Long id);
    Basket findBasketByUsernameAndItemid(String username, Long itemid);
    Basket findBasketById(Long id);

}
