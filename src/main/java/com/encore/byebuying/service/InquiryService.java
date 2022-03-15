package com.encore.byebuying.service;

import com.encore.byebuying.domain.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Date;

public interface InquiryService {
    Inquiry saveInquiry(Inquiry inquiry);
    Inquiry getById(Long id);

    Page<Inquiry> getInquiry(Pageable pageable); // 전체 조회
    Page<Inquiry> getByItemid(Pageable pageable, Long itemid);
    Page<Inquiry> getByItemid(Pageable pageable, Long itemid, int chkAnswer);
    Page<Inquiry> getByItemid(Pageable pageable, String start, String end, Long itemid) throws ParseException;
    Page<Inquiry> getByItemid(Pageable pageable, String start, String end, Long itemid, int chkAnswer) throws ParseException;

    Page<Inquiry> getByUsername(Pageable pageable, String username);
    Page<Inquiry> getByUsername(Pageable pageable, String username, int chkAnswer);
    Page<Inquiry> getByUsername(Pageable pageable, String start, String end, String username) throws ParseException;
    Page<Inquiry> getByUsername(Pageable pageable, String start, String end, String username, int chkAnswer) throws ParseException;

    Page<Inquiry> getByUsernameAndItemid(Pageable pageable, String username, Long itemid);
    Page<Inquiry> getByUsernameAndItemid(Pageable pageable, String username, int chkAnswer, Long itemid);
    Page<Inquiry> getByUsernameAndItemid(Pageable pageable, String start, String end, String username, Long itemid) throws ParseException;
    Page<Inquiry> getByUsernameAndItemid(Pageable pageable, String start, String end, String username, int chkAnswer, Long itemid) throws ParseException;

    void deleteInquiryById(Long id);
}
