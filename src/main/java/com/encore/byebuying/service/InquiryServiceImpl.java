package com.encore.byebuying.service;

import com.encore.byebuying.domain.Inquiry;
import com.encore.byebuying.repo.InquiryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service @RequiredArgsConstructor @Slf4j
public class InquiryServiceImpl implements InquiryService{
    private final InquiryRepo inquiryRepo;

    @Override
    public Inquiry saveInquiry(Inquiry inquiry){
        log.info("Saving new Inquiry");
        return inquiryRepo.save(inquiry);
    }

    @Override
    public Inquiry getById(Long id) {
        return inquiryRepo.findInquiryById(id);
    }

    @Override
    public Page<Inquiry> getByItemid(Pageable pageable, Long itemid) {
        return inquiryRepo.findByItemid(pageable, itemid);
    }

    @Override
    public Page<Inquiry> getByUsername(Pageable pageable, String username) {
        return inquiryRepo.findByUsername(pageable, username);
    }

    @Override
    public Page<Inquiry> getByUsernameAndBetweenDate(
            Pageable pageable, String start, String end, String username) throws ParseException {
        log.info("Get Inquiry Date start: {}, end: {}", start, end);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        return inquiryRepo.findByDateBetweenAndUsername(pageable, dateStart, dateEnd, username);
    }

    @Override
    public void deleteInquiryById(Long id) {
        log.info("Delete Inquiry By Id : {}", id);
        inquiryRepo.deleteById(id);
    }
}
