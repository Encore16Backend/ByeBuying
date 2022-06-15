package com.encore.byebuying.domain.inquiry.service;

import com.encore.byebuying.domain.inquiry.dto.InquirySaveDTO;
import com.encore.byebuying.domain.inquiry.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryService {
    Inquiry saveInquiry(InquirySaveDTO inquirySaveDTO);
    void deleteInquiryById(Long id);
    Inquiry getById(Long id);
    void answerToInquiry(Long inquiry_id, String answer);

// Item과 무관
//    Page<Inquiry> getByItemid(Pageable pageable, Long item_id);
//    Page<Inquiry> getByItemName(Pageable pageable, String itemName);
    Page<Inquiry> getByUserId(Pageable pageable, Long user_id);
    Page<Inquiry> getInquiries(Pageable pageable);





//    Page<Inquiry> getByItemid(Pageable pageable, Long itemid, int chkAnswer);
//    Page<Inquiry> getByItemid(Pageable pageable, String start, String end, Long itemid) throws ParseException;
//    Page<Inquiry> getByItemid(Pageable pageable, String start, String end, Long itemid, int chkAnswer) throws ParseException;
//    Page<Inquiry> getByItemname(Pageable pageable, String itemname, int chkAnswer);
//    Page<Inquiry> getByItemname(Pageable pageable, String start, String end, String itemname) throws ParseException;
//    Page<Inquiry> getByItemname(Pageable pageable, String start, String end, String itemname, int chkAnswer) throws ParseException;
//    Page<Inquiry> getInquiries(Pageable pageable, int chkAnswer);
//    Page<Inquiry> getInquiries(Pageable pageable, String start, String end) throws ParseException;
//    Page<Inquiry> getInquiries(Pageable pageable, String start, String end, int chkAnswer) throws ParseException;
//    Page<Inquiry> getByUsername(Pageable pageable, String username);
//    Page<Inquiry> getByUsername(Pageable pageable, String username, int chkAnswer);
//    Page<Inquiry> getByUsername(Pageable pageable, String start, String end, String username) throws ParseException;
//    Page<Inquiry> getByUsername(Pageable pageable, String start, String end, String username, int chkAnswer) throws ParseException;
//    Page<Inquiry> getByUsernameAndItemname(Pageable pageable, String username, String itemname);
//    Page<Inquiry> getByUsernameAndItemname(Pageable pageable, String username, int chkAnswer, String itemname);
//    Page<Inquiry> getByUsernameAndItemname(Pageable pageable, String start, String end, String username, String itemname) throws ParseException;
//    Page<Inquiry> getByUsernameAndItemname(Pageable pageable, String start, String end, String username, int chkAnswer, String itemname) throws ParseException;

}
