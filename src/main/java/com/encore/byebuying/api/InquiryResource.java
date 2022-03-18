package com.encore.byebuying.api;

import com.encore.byebuying.domain.Inquiry;
import com.encore.byebuying.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryResource {
    private final InquiryService inquiryService;
    private final int PAGECOUNT = 5;

    @PostMapping("/save")
    public String saveInquiry(@RequestBody Inquiry inquiry){
        inquiry.setChkanswer(0);
        inquiry.setDate(new Date());
        inquiry.setAnswer("");
        inquiryService.saveInquiry(inquiry);
        return "SUCCESS";
    }

    @Transactional
    @PutMapping("/answer")
    public String answerInquiry(@RequestBody Inquiry answerInquiry) {
        Inquiry inquiry = inquiryService.getById(answerInquiry.getId());
        inquiry.setAnswer(answerInquiry.getAnswer());
        inquiry.setChkanswer(1);
        inquiryService.saveInquiry(inquiry);
        return "SUCCESS";
    }

    @GetMapping("/byItemid")
    public ResponseEntity<Page<Inquiry>> getInquiryByItemid(
            @RequestParam(defaultValue = "", value = "itemid") Long itemid,
            @RequestParam(required = false, defaultValue = "", value = "start") String start,
            @RequestParam(required = false, defaultValue = "", value = "end") String end,
            @RequestParam(required = false, defaultValue = "-1", value = "chkAnswer") int chkAnswer,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
                            Sort.by(Sort.Direction.ASC, "date"));
        Page<Inquiry> inquiries;
        if (start.equals("") && end.equals("")){
            if (chkAnswer == -1){
                inquiries = inquiryService.getByItemid(pageable, itemid);
            } else {
                inquiries = inquiryService.getByItemid(pageable, itemid, chkAnswer);
            }
        } else {
            if (chkAnswer == -1){
                inquiries = inquiryService.getByItemid(pageable, start, end, itemid);
            } else {
                inquiries = inquiryService.getByItemid(pageable, start, end, itemid, chkAnswer);
            }
        }
        return ResponseEntity.ok().body(inquiries);
    }

    @GetMapping("/getInquiries")
    public ResponseEntity<Page<Inquiry>> getInquiryByUsername( // 관리 관련 페이지에서 사용할 API
            @RequestParam(required = false, defaultValue = "", value = "itemname") String itemname,
            @RequestParam(required = false, defaultValue = "", value = "username") String username,
            @RequestParam(required = false, defaultValue = "", value = "start") String start,
            @RequestParam(required = false, defaultValue = "", value = "end") String end,
            @RequestParam(required = false, defaultValue = "-1", value = "chkAnswer") int chkAnswer,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
                Sort.by(Sort.Direction.ASC, "date"));
        Page<Inquiry> inquiries;

        if (username.equals("") && itemname.equals("")) { // 유저아이디도 없고, 아이템 아이디도 없을 때
            if (start.equals("") || end.equals("")){
                if (chkAnswer == -1) inquiries = inquiryService.getInquiries(pageable);
                else inquiries = inquiryService.getInquiries(pageable, chkAnswer);
            } else {
                if (chkAnswer == -1) inquiries = inquiryService.getInquiries(pageable, start, end);
                else inquiries = inquiryService.getInquiries(pageable, start, end, chkAnswer);
            }
        } else if (username.equals("")){ // 유저아이디 없고, 아이템 아이디만 있을 때
            if (start.equals("") || end.equals("")){
                if (chkAnswer == -1) inquiries = inquiryService.getByItemname(pageable, itemname);
                else inquiries = inquiryService.getByItemname(pageable, itemname, chkAnswer);
            } else {
                if (chkAnswer == -1) inquiries = inquiryService.getByItemname(pageable, start, end, itemname);
                else inquiries = inquiryService.getByItemname(pageable, start, end, itemname, chkAnswer);
            }
        } else if (itemname.equals("")) { // 유저아이디는 있고, 아이템 아이디는 없을 때
            if (start.equals("") || end.equals("")){
                if (chkAnswer == -1) inquiries = inquiryService.getByUsername(pageable, username);
                else inquiries = inquiryService.getByUsername(pageable, username, chkAnswer);
            } else {
                if (chkAnswer == -1) inquiries = inquiryService.getByUsername(pageable, start, end, username);
                else inquiries = inquiryService.getByUsername(pageable, start, end, username, chkAnswer);
            }
        } else { // 유저아이디 존재, 아이템 아이디도 존재할 때
            if (start.equals("") || end.equals("")){
                if (chkAnswer == -1) inquiries = inquiryService.getByUsernameAndItemname(pageable, username, itemname);
                else inquiries = inquiryService.getByUsernameAndItemname(pageable, username, chkAnswer, itemname);
            } else {
                if (chkAnswer == -1) inquiries = inquiryService.getByUsernameAndItemname(pageable, start, end, username, itemname);
                else inquiries = inquiryService.getByUsernameAndItemname(pageable, start, end, username, chkAnswer, itemname);
            }
        }
        
        return ResponseEntity.ok().body(inquiries);
    }
}
