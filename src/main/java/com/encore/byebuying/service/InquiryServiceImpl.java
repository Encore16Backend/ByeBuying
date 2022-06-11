package com.encore.byebuying.service;

import com.encore.byebuying.api.dto.Inquiry.InquirySaveRequest;
import com.encore.byebuying.domain.Inquiry;
import com.encore.byebuying.domain.Item;
import com.encore.byebuying.domain.User;
import com.encore.byebuying.repo.InquiryRepo;
import com.encore.byebuying.repo.ItemRepository;
import com.encore.byebuying.repo.UserRepo;
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
    private final UserRepo userRepo;
    private final ItemRepository itemRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * parmas user_id, item_id, content, title
     * */
    @Override
    public Inquiry saveInquiry(InquirySaveRequest inquirySaveRequest) {
        Long user_id = inquirySaveRequest.getUser_id();
        Long item_id = inquirySaveRequest.getItem_id();
        String content = inquirySaveRequest.getContent();
        String title = inquirySaveRequest.getTitle();

        User user = userRepo.getById(user_id);
        Item item = itemRepository.getById(item_id);

        Inquiry inquiry = Inquiry.createInquiry(user, item, title, content);

        return inquiryRepo.save(inquiry);
    }

    @Override
    public void deleteInquiryById(Long id) {
        inquiryRepo.deleteById(id);
    }

    @Override
    public Inquiry getById(Long id) {
        return inquiryRepo.getById(id);
    }

    @Override
    public Page<Inquiry> getInquiries(Pageable pageable) {
        return inquiryRepo.findAll(pageable);
    }

    @Override
    public Page<Inquiry> getByItemid(Pageable pageable, Long item_id) {
        return inquiryRepo.findByItemId(pageable, item_id);
    }

    @Override
    public Page<Inquiry> getByUserId(Pageable pageable, Long user_id) {
        return inquiryRepo.findByUserId(pageable, user_id);
    }

    @Override
    public Page<Inquiry> getByItemName(Pageable pageable, String itemName) {
        return inquiryRepo.findByItemName(pageable, itemName);
    }



    @Override
    public void answerToInquiry(Long inquiry_id, String answer) {
        Inquiry inquiry = inquiryRepo.getById(inquiry_id);
        inquiry.setChkanswer(1);
        inquiry.setAnswer(answer);
    }





//    @Override
//    public Inquiry saveInquiry(Inquiry inquiry){
//        log.info("Saving new Inquiry");
//        return inquiryRepo.save(inquiry);
//    }
//
//    @Override
//    public Inquiry getById(Long id) {
//        return inquiryRepo.findInquiryById(id);
//    }
//
//    @Override
//    public Page<Inquiry> getByItemid(Pageable pageable, Long itemid) {
//        return inquiryRepo.findByItemid(pageable, itemid);
//    }
//
//    @Override
//    public Page<Inquiry> getByItemid(Pageable pageable, Long itemid, int chkAnswer) {
//        return inquiryRepo.findByItemidAndChkanswer(pageable, itemid, chkAnswer);
//    }
//
//    @Override
//    public Page<Inquiry> getByItemid(Pageable pageable, String start, String end, Long itemid) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetweenAndItemid(pageable, dateStart, dateEnd, itemid);
//    }
//
//    @Override
//    public Page<Inquiry> getByItemid(Pageable pageable, String start, String end, Long itemid, int chkAnswer) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetweenAndItemidAndChkanswer(pageable, dateStart, dateEnd, itemid, chkAnswer);
//    }
//
//    @Override
//    public Page<Inquiry> getByItemname(Pageable pageable, String itemname) {
//        return inquiryRepo.findByItemnameContainingIgnoreCase(pageable, itemname);
//    }
//
//    @Override
//    public Page<Inquiry> getByItemname(Pageable pageable, String itemname, int chkAnswer) {
//        return inquiryRepo.findByItemnameContainingIgnoreCaseAndChkanswer(pageable, itemname, chkAnswer);
//    }
//
//    @Override
//    public Page<Inquiry> getByItemname(Pageable pageable, String start, String end, String itemname) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetweenAndItemnameContainingIgnoreCase(pageable, dateStart, dateEnd, itemname);
//    }
//
//    @Override
//    public Page<Inquiry> getByItemname(Pageable pageable, String start, String end, String itemname, int chkAnswer) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetweenAndItemnameContainingIgnoreCaseAndChkanswer(pageable, dateStart, dateEnd, itemname, chkAnswer);
//    }
//
//    @Override
//    public Page<Inquiry> getInquiries(Pageable pageable) {
//        return inquiryRepo.findAll(pageable);
//    }
//
//    @Override
//    public Page<Inquiry> getInquiries(Pageable pageable, int chkAnswer) {
//        return inquiryRepo.findByChkanswer(pageable, chkAnswer);
//    }
//
//    @Override
//    public Page<Inquiry> getInquiries(Pageable pageable, String start, String end) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetween(pageable, dateStart, dateEnd);
//    }
//
//    @Override
//    public Page<Inquiry> getInquiries(Pageable pageable, String start, String end, int chkAnswer) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetweenAndChkanswer(pageable, dateStart, dateEnd, chkAnswer);
//    }
//
//    @Override
//    public Page<Inquiry> getByUsername(Pageable pageable, String username) {
//        return inquiryRepo.findByUsername(pageable, username);
//    }
//
//    @Override
//    public Page<Inquiry> getByUsername(Pageable pageable, String username, int chkAnswer) {
//        return inquiryRepo.findByUsernameAndChkanswer(pageable, username, chkAnswer);
//    }
//
//    @Override
//    public Page<Inquiry> getByUsername(Pageable pageable, String start, String end, String username) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetweenAndUsername(pageable, dateStart, dateEnd, username);
//    }
//
//    @Override
//    public Page<Inquiry> getByUsername(Pageable pageable, String start, String end, String username, int chkAnswer) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetweenAndUsernameAndChkanswer(pageable, dateStart, dateEnd, username, chkAnswer);
//    }
//
//
//
//    @Override
//    public Page<Inquiry> getByUsernameAndItemname(Pageable pageable, String username, String itemname) {
//        return inquiryRepo.findByUsernameAndItemnameContainingIgnoreCase(pageable, username, itemname);
//    }
//
//    @Override
//    public Page<Inquiry> getByUsernameAndItemname(Pageable pageable, String username, int chkAnswer, String itemname) {
//        return inquiryRepo.findByUsernameAndChkanswerAndItemnameContainingIgnoreCase(pageable, username, chkAnswer, itemname);
//    }
//
//    @Override
//    public Page<Inquiry> getByUsernameAndItemname(Pageable pageable, String start, String end, String username, String itemname) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetweenAndUsernameAndItemnameContainingIgnoreCase(pageable, dateStart, dateEnd, username, itemname);
//    }
//
//    @Override
//    public Page<Inquiry> getByUsernameAndItemname(Pageable pageable, String start, String end, String username, int chkAnswer, String itemname) throws ParseException {
//        log.info("Get Inquiry Date start: {}, end: {}", start, end);
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        return inquiryRepo.findByDateBetweenAndUsernameAndChkanswerAndItemnameContainingIgnoreCase(pageable, dateStart, dateEnd, username, chkAnswer, itemname);
//    }
//
//    @Override
//    public void deleteInquiryById(Long id) {
//        log.info("Delete Inquiry By Id : {}", id);
//        inquiryRepo.deleteById(id);
//    }
}
