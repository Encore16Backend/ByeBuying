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
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
    public Page<Inquiry> getByItemid(Pageable pageable, Long itemid, int chkAnswer) {
        return inquiryRepo.findByItemidAndChkanswer(pageable, itemid, chkAnswer);
    }

    @Override
    public Page<Inquiry> getByItemid(Pageable pageable, String start, String end, Long itemid) throws ParseException {
        log.info("Get Inquiry Date start: {}, end: {}", start, end);
        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        return inquiryRepo.findByDateBetweenAndItemid(pageable, dateStart, dateEnd, itemid);
    }

    @Override
    public Page<Inquiry> getByItemid(Pageable pageable, String start, String end, Long itemid, int chkAnswer) throws ParseException {
        log.info("Get Inquiry Date start: {}, end: {}", start, end);
        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        return inquiryRepo.findByDateBetweenAndItemidAndChkanswer(pageable, dateStart, dateEnd, itemid, chkAnswer);
    }

    @Override
    public Page<Inquiry> getInquiries(Pageable pageable) {
        return inquiryRepo.findAll(pageable);
    }

    @Override
    public Page<Inquiry> getInquiries(Pageable pageable, int chkAnswer) {
        return inquiryRepo.findByChkanswer(pageable, chkAnswer);
    }

    @Override
    public Page<Inquiry> getInquiries(Pageable pageable, String start, String end) throws ParseException {
        log.info("Get Inquiry Date start: {}, end: {}", start, end);
        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        return inquiryRepo.findByDateBetween(pageable, dateStart, dateEnd);
    }

    @Override
    public Page<Inquiry> getInquiries(Pageable pageable, String start, String end, int chkAnswer) throws ParseException {
        log.info("Get Inquiry Date start: {}, end: {}", start, end);
        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        return inquiryRepo.findByDateBetweenAndChkanswer(pageable, dateStart, dateEnd, chkAnswer);
    }

    @Override
    public Page<Inquiry> getByUsername(Pageable pageable, String username) {
        return inquiryRepo.findByUsername(pageable, username);
    }

    @Override
    public Page<Inquiry> getByUsername(Pageable pageable, String username, int chkAnswer) {
        return inquiryRepo.findByUsernameAndChkanswer(pageable, username, chkAnswer);
    }

    @Override
    public Page<Inquiry> getByUsername(Pageable pageable, String start, String end, String username) throws ParseException {
        log.info("Get Inquiry Date start: {}, end: {}", start, end);
        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        return inquiryRepo.findByDateBetweenAndUsername(pageable, dateStart, dateEnd, username);
    }

    @Override
    public Page<Inquiry> getByUsername(Pageable pageable, String start, String end, String username, int chkAnswer) throws ParseException {
        log.info("Get Inquiry Date start: {}, end: {}", start, end);
        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        return inquiryRepo.findByDateBetweenAndUsernameAndChkanswer(pageable, dateStart, dateEnd, username, chkAnswer);
    }

    @Override
    public Page<Inquiry> getByUsernameAndItemid(Pageable pageable, String username, Long itemid) {
        return inquiryRepo.findByUsernameAndItemid(pageable, username, itemid);
    }

    @Override
    public Page<Inquiry> getByUsernameAndItemid(Pageable pageable, String username, int chkAnswer, Long itemid) {
        return inquiryRepo.findByUsernameAndChkanswerAndItemid(pageable, username, chkAnswer, itemid);
    }

    @Override
    public Page<Inquiry> getByUsernameAndItemid(Pageable pageable, String start, String end, String username, Long itemid) throws ParseException {
        log.info("Get Inquiry Date start: {}, end: {}", start, end);
        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        return inquiryRepo.findByDateBetweenAndUsernameAndItemid(pageable, dateStart, dateEnd, username, itemid);
    }

    @Override
    public Page<Inquiry> getByUsernameAndItemid(Pageable pageable, String start, String end, String username, int chkAnswer, Long itemid) throws ParseException {
        log.info("Get Inquiry Date start: {}, end: {}", start, end);
        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        return inquiryRepo.findByDateBetweenAndUsernameAndChkanswerAndItemid(pageable, dateStart, dateEnd, username, chkAnswer, itemid);
    }

    @Override
    public void deleteInquiryById(Long id) {
        log.info("Delete Inquiry By Id : {}", id);
        inquiryRepo.deleteById(id);
    }
}
