package com.encore.byebuying.domain.inquiry.repository;

import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.dto.InquiryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("select i from Inquiry i where i.user.id = :userId")
    Page<Inquiry> findByUserId(Pageable pageable, @Param("userId") Long userId);

//    Inquiry와 Item은 무관
//    @Query("select i from Inquiry i where i.item.id = :itemId")
//    Page<Inquiry> findByItemId(Pageable pageable, @Param("itemId") Long itemId);
//
//    @Query("select i from Inquiry i where i.item.name like %:itemName%")
//    Page<Inquiry> findByItemName(Pageable pageable, @Param("itemName") String itemName);




//    Inquiry findInquiryById(Long id);
//    Page<Inquiry> findByItemid(Pageable pageable, Long itemid);
//    Page<Inquiry> findByItemidAndChkanswer(Pageable pageable, Long itemid, int chkAnswer);
//    Page<Inquiry> findByDateBetweenAndItemid(Pageable pageable, Date start, Date end, Long itemid);
//    Page<Inquiry> findByDateBetweenAndItemidAndChkanswer(Pageable pageable, Date start, Date end, Long itemid, int chkAnswer);
//    Page<Inquiry> findByItemnameContainingIgnoreCase(
//            Pageable pageable, String itemname);
//    Page<Inquiry> findByItemnameContainingIgnoreCaseAndChkanswer(
//            Pageable pageable, String itemname, int chkAnswer);
//    Page<Inquiry> findByDateBetweenAndItemnameContainingIgnoreCase(
//            Pageable pageable, Date start, Date end, String itemname);
//    Page<Inquiry> findByDateBetweenAndItemnameContainingIgnoreCaseAndChkanswer(
//            Pageable pageable, Date start, Date end, String itemname, int chkAnswer);
//    Page<Inquiry> findAll(Pageable pageable);
//    Page<Inquiry> findByChkanswer(Pageable pageable, int chkAnswer);
//    Page<Inquiry> findByDateBetween(Pageable pageable, Date start, Date end);
//    Page<Inquiry> findByDateBetweenAndChkanswer(Pageable pageable, Date start, Date end, int chkAnswer);
//    Page<Inquiry> findByUsername(Pageable pageable, String username);
//    Page<Inquiry> findByUsernameAndChkanswer(Pageable pageable, String username, int chkAnswer);
//    Page<Inquiry> findByDateBetweenAndUsername(Pageable pageable, Date start, Date end, String username);
//    Page<Inquiry> findByDateBetweenAndUsernameAndChkanswer(Pageable pageable, Date start, Date end, String username, int chkAnswer);
//    Page<Inquiry> findByUsernameAndItemnameContainingIgnoreCase(
//            Pageable pageable, String username, String itemname);
//    Page<Inquiry> findByUsernameAndChkanswerAndItemnameContainingIgnoreCase(
//            Pageable pageable, String username, int chkAnswer, String itemname);
//    Page<Inquiry> findByDateBetweenAndUsernameAndItemnameContainingIgnoreCase(
//            Pageable pageable, Date start, Date end, String username, String itemname);
//    Page<Inquiry> findByDateBetweenAndUsernameAndChkanswerAndItemnameContainingIgnoreCase(
//            Pageable pageable, Date start, Date end, String username, int chkAnswer, String itemname);
//    void deleteAllByUsername(String username);
}
