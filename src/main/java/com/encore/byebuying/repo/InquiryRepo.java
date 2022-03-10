package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface InquiryRepo extends JpaRepository<Inquiry, Long> {
    Inquiry findInquiryById(Long id);

    Page<Inquiry> findByItemid(Pageable pageable, Long itemid);
    Page<Inquiry> findByUsername(Pageable pageable, String username);

    Page<Inquiry> findByDateBetweenAndUsername(Pageable pageable, Date start, Date end, String username);

    void deleteAllByUsername(String username);
}
