package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface InquiryRepo extends JpaRepository<Inquiry, Long> {
    Inquiry findInquiryById(Long id);

    Page<Inquiry> findByItemid(Pageable pageable, Long itemid);
    Page<Inquiry> findByItemidAndChkanswer(Pageable pageable, Long itemid, int chkAnswer);
    Page<Inquiry> findByDateBetweenAndItemid(Pageable pageable, Date start, Date end, Long itemid);
    Page<Inquiry> findByDateBetweenAndItemidAndChkanswer(Pageable pageable, Date start, Date end, Long itemid, int chkAnswer);

    Page<Inquiry> findByUsername(Pageable pageable, String username);
    Page<Inquiry> findByUsernameAndChkanswer(Pageable pageable, String username, int chkAnswer);
    Page<Inquiry> findByDateBetweenAndUsername(Pageable pageable, Date start, Date end, String username);
    Page<Inquiry> findByDateBetweenAndUsernameAndChkanswer(Pageable pageable, Date start, Date end, String username, int chkAnswer);

    Page<Inquiry> findByUsernameAndItemid(Pageable pageable, String username, Long itemid);
    Page<Inquiry> findByUsernameAndChkanswerAndItemid(Pageable pageable, String username, int chkAnswer, Long itemid);
    Page<Inquiry> findByDateBetweenAndUsernameAndItemid(Pageable pageable, Date start, Date end, String username, Long itemid);
    Page<Inquiry> findByDateBetweenAndUsernameAndChkanswerAndItemid(Pageable pageable, Date start, Date end, String username, int chkAnswer, Long itemid);

    void deleteAllByUsername(String username);
}
