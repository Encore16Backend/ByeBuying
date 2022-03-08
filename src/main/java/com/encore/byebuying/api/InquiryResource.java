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
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
                            Sort.by(Sort.Direction.ASC, "date"));
        Page<Inquiry> inquiries = inquiryService.getByItemid(pageable, itemid);
        return ResponseEntity.ok().body(inquiries);
    }

    @GetMapping("/byUsername")
    public ResponseEntity<Page<Inquiry>> getInquiryByUsername(
            @RequestParam(defaultValue = "", value = "username") String username,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
                Sort.by(Sort.Direction.ASC, "date"));
        Page<Inquiry> inquiries = inquiryService.getByUsername(pageable, username);
        return ResponseEntity.ok().body(inquiries);
    }

    @GetMapping("/byDate")
    public ResponseEntity<Page<Inquiry>> getInquiryByDate(
            @RequestParam(defaultValue = "", value = "username") String username,
            @RequestParam(defaultValue = "", value = "start") String start,
            @RequestParam(defaultValue = "", value = "end") String end,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
                Sort.by(Sort.Direction.ASC, "date"));
        Page<Inquiry> inquiries = inquiryService.getByUsernameAndBetweenDate(pageable, start, end, username);
        return ResponseEntity.ok().body(inquiries);
    }
}
