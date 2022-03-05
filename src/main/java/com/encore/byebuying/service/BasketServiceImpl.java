package com.encore.byebuying.service;

import com.encore.byebuying.domain.Basket;
import com.encore.byebuying.repo.BasketRepo;
import com.encore.byebuying.repo.ItemRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BasketServiceImpl implements BasketService{
    private final BasketRepo basketRepo;
    private final ItemRepo itemRepo;

    @Override
    public Basket saveBasket(Basket basket) {
        log.info("{}: Add item {} into Basket", basket.getUsername(), basket.getItemid());
        Basket checkBasket = basketRepo.findBasketByItemid(basket.getItemid());
        if(checkBasket != null){
            checkBasket.setBcount(checkBasket.getBcount()+basket.getBcount());
            return basketRepo.save(checkBasket);
        }
        return basketRepo.save(basket);
    }

    @Override
    public Page<Basket> getByUsername(Pageable pageable, String username) {
        return basketRepo.findByUsername(pageable, username);
    }

    @Override
    public Basket getBasket(Long id) {
        return basketRepo.findBasketByItemid(id);
    }

    @Override
    public void deleteBasket(Long id) {
        log.info("Delete Basket By id {}", id);
        basketRepo.deleteById(id);
    }
}
