package com.encore.byebuying.service;

import com.encore.byebuying.domain.Basket;
import com.encore.byebuying.repo.BasketRepo;
import com.encore.byebuying.repo.ItemRepository;
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
    private final ItemRepository itemRepository;

    @Override
    public Basket saveBasket(Basket basket, String mode) {
        if (mode.equals("save")){
            Basket checkBasket = basketRepo.findBasketByUsernameAndItemid(basket.getUsername(), basket.getItemid());
            if(checkBasket != null){
                log.info("Save Mode, Basket Exist");
                checkBasket.setBcount(checkBasket.getBcount()+basket.getBcount());
                return basketRepo.save(checkBasket);
            }
            log.info("Save Mode, Basket Not Exist");
            return basketRepo.save(basket);
        }
        log.info("Update Mode");
        return basketRepo.save(basket);
    }

    @Override
    public Page<Basket> getByUsername(Pageable pageable, String username) {
        return basketRepo.findByUsername(pageable, username);
    }

    @Override
    public Basket getBasketById(Long id) {
        return basketRepo.findBasketById(id);
    }

    @Override
    public void deleteBasket(Long id) {
        log.info("Delete Basket By id {}", id);
        basketRepo.deleteById(id);
    }

    @Override
    public void deleteBasketByItemidAndUsername(Long itemid, String username) {
        log.info("Delete Basket By Buy Item, itemid: {}, username: {}", itemid, username);
        basketRepo.deleteByItemidAndUsername(itemid, username);
    }


}
