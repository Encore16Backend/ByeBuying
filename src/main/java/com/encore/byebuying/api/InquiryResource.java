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

    @GetMapping("/all")


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

    @GetMapping("/byUsername")
    public ResponseEntity<Page<Inquiry>> getInquiryByUsername(
            @RequestParam(defaultValue = "", value = "username") String username,
            @RequestParam(required = false, defaultValue = "", value = "start") String start,
            @RequestParam(required = false, defaultValue = "", value = "end") String end,
            @RequestParam(required = false, defaultValue = "-1", value = "chkAnswer") int chkAnswer,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
                Sort.by(Sort.Direction.ASC, "date"));
        Page<Inquiry> inquiries;
        if (start.equals("") && end.equals("")){
            if (chkAnswer == -1){
                inquiries = inquiryService.getByUsername(pageable, username);
            } else {
                inquiries = inquiryService.getByUsername(pageable, username, chkAnswer);
            }
        } else {
            if (chkAnswer == -1){
                inquiries = inquiryService.getByUsername(pageable, start, end, username);
            } else {
                inquiries = inquiryService.getByUsername(pageable, start, end, username, chkAnswer);
            }
        }
        return ResponseEntity.ok().body(inquiries);
    }

    @GetMapping("/byUserNItemid")
    public ResponseEntity<Page<Inquiry>> getInquiryByUsernameAndItemid(
            @RequestParam(defaultValue = "", value = "itemid") Long itemid,
            @RequestParam(defaultValue = "", value = "username") String username,
            @RequestParam(required = false, defaultValue = "", value = "start") String start,
            @RequestParam(required = false, defaultValue = "", value = "end") String end,
            @RequestParam(required = false, defaultValue = "-1", value = "chkAnswer") int chkAnswer,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
                Sort.by(Sort.Direction.ASC, "date"));
        Page<Inquiry> inquiries;
        if (start.equals("") && end.equals("")){
            if (chkAnswer == -1){
                inquiries = inquiryService.getByUsernameAndItemid(pageable, username, itemid);
            } else {
                inquiries = inquiryService.getByUsernameAndItemid(pageable, username, chkAnswer, itemid);
            }
        } else {
            if (chkAnswer == -1){
                inquiries = inquiryService.getByUsernameAndItemid(pageable, start, end, username, itemid);
            } else {
                inquiries = inquiryService.getByUsernameAndItemid(pageable, start, end, username, chkAnswer, itemid);
            }
        }
        return ResponseEntity.ok().body(inquiries);
    }
}
