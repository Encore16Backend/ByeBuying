package com.encore.byebuying.service;

import com.encore.byebuying.domain.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Date;

public interface InquiryService {
    Inquiry saveInquiry(Inquiry inquiry);

    Inquiry getById(Long id);
    Page<Inquiry> getByItemid(Pageable pageable, Long itemid);
    Page<Inquiry> getByUsername(Pageable pageable, String username);
    Page<Inquiry> getByUsernameAndBetweenDate(Pageable pageable, String start, String end, String username) throws ParseException;

    void deleteInquiryById(Long id);
}
