package com.encore.byebuying.domain.inquiry.controller;

import com.encore.byebuying.domain.inquiry.dto.InquirySaveDTO;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;
    private final int PAGECOUNT = 5;

    @PostMapping("/save")
    public ResponseEntity<?> saveInquiry(@RequestBody InquirySaveDTO inquirySaveDTO){
        inquiryService.saveInquiry(inquirySaveDTO);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public void deleteInquiry(Long inquiryId){
        inquiryService.deleteInquiryById(inquiryId);
    }

    @GetMapping("/one")
    public ResponseEntity<?> getInquiryById(Long id){
        Inquiry inquiry = inquiryService.getById(id);
        return new ResponseEntity<>(inquiry, HttpStatus.OK);
    }

    @PostMapping("/answer")
    public ResponseEntity<?> answerToInquiry(Long inquiry_id, String answer){
        inquiryService.answerToInquiry( inquiry_id,  answer);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getInquirys(@RequestParam(required = false, defaultValue="1",value="page") int page) {
        PageRequest pageRequest = PageRequest.of(page-1, PAGECOUNT);
        Page<Inquiry> inquiries = inquiryService.getInquiries(pageRequest);
        return new ResponseEntity<>(inquiries, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getByUserId(@RequestParam(required = false, defaultValue="1",value="page") int page,
    Long user_id) {
        PageRequest pageRequest = PageRequest.of(page-1, PAGECOUNT);
        Page<Inquiry> inquiries = inquiryService.getByUserId(pageRequest, user_id);
        return new ResponseEntity<>(inquiries, HttpStatus.OK);
    }

    // 지금 필요없어서 주석 처리
//    @GetMapping("/item")
//    public ResponseEntity<?> getByItemId(@RequestParam(required = false, defaultValue="1",value="page") int page,
//                                         Long item_id) {
//        PageRequest pageRequest = PageRequest.of(page-1, PAGECOUNT);
//        Page<Inquiry> inquiries = inquiryService.getByItemid(pageRequest, item_id);
//        return new ResponseEntity<>(inquiries, HttpStatus.OK);
//    }

//    @GetMapping("/itemname")
//    public ResponseEntity<?> getByItemName(@RequestParam(required = false, defaultValue="1",value="page") int page,
//                                         String itemName) {
//        PageRequest pageRequest = PageRequest.of(page-1, PAGECOUNT);
//        Page<Inquiry> inquiries = inquiryService.getByItemName(pageRequest, itemName);
//        return new ResponseEntity<>(inquiries, HttpStatus.OK);
//    }



//
//    @Transactional
//    @PutMapping("/answer")
//    public String answerInquiry(@RequestBody Inquiry answerInquiry) {
//        Inquiry inquiry = inquiryService.getById(answerInquiry.getId());
//        inquiry.setAnswer(answerInquiry.getAnswer());
//        inquiry.setChkanswer(1);
//        inquiryService.saveInquiry(inquiry);
//        return "SUCCESS";
//    }
//
//    @GetMapping("/byItemid")
//    public ResponseEntity<Page<Inquiry>> getInquiryByItemid(
//            @RequestParam(defaultValue = "", value = "itemid") Long itemid,
//            @RequestParam(required = false, defaultValue = "", value = "start") String start,
//            @RequestParam(required = false, defaultValue = "", value = "end") String end,
//            @RequestParam(required = false, defaultValue = "-1", value = "chkAnswer") int chkAnswer,
//            @RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
//        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
//                            Sort.by(Sort.Direction.ASC, "date"));
//        Page<Inquiry> inquiries;
//        if (start.equals("") && end.equals("")){
//            if (chkAnswer == -1){
//                inquiries = inquiryService.getByItemid(pageable, itemid);
//            } else {
//                inquiries = inquiryService.getByItemid(pageable, itemid, chkAnswer);
//            }
//        } else {
//            if (chkAnswer == -1){
//                inquiries = inquiryService.getByItemid(pageable, start, end, itemid);
//            } else {
//                inquiries = inquiryService.getByItemid(pageable, start, end, itemid, chkAnswer);
//            }
//        }
//        return ResponseEntity.ok().body(inquiries);
//    }
//
//    @GetMapping("/getInquiries")
//    public ResponseEntity<Page<Inquiry>> getInquiryByUsername( // 관리 관련 페이지에서 사용할 API
//            @RequestParam(required = false, defaultValue = "", value = "itemname") String itemname,
//            @RequestParam(required = false, defaultValue = "", value = "username") String username,
//            @RequestParam(required = false, defaultValue = "", value = "start") String start,
//            @RequestParam(required = false, defaultValue = "", value = "end") String end,
//            @RequestParam(required = false, defaultValue = "-1", value = "chkAnswer") int chkAnswer,
//            @RequestParam(required = false, defaultValue = "1", value = "page") int page) throws ParseException {
//        Pageable pageable = PageRequest.of(page-1, PAGECOUNT,
//                Sort.by(Sort.Direction.ASC, "date"));
//        Page<Inquiry> inquiries;
//
//        if (username.equals("") && itemname.equals("")) { // 유저아이디도 없고, 아이템 아이디도 없을 때
//            if (start.equals("") || end.equals("")){
//                if (chkAnswer == -1) inquiries = inquiryService.getInquiries(pageable);
//                else inquiries = inquiryService.getInquiries(pageable, chkAnswer);
//            } else {
//                if (chkAnswer == -1) inquiries = inquiryService.getInquiries(pageable, start, end);
//                else inquiries = inquiryService.getInquiries(pageable, start, end, chkAnswer);
//            }
//        } else if (username.equals("")){ // 유저아이디 없고, 아이템 아이디만 있을 때
//            if (start.equals("") || end.equals("")){
//                if (chkAnswer == -1) inquiries = inquiryService.getByItemname(pageable, itemname);
//                else inquiries = inquiryService.getByItemname(pageable, itemname, chkAnswer);
//            } else {
//                if (chkAnswer == -1) inquiries = inquiryService.getByItemname(pageable, start, end, itemname);
//                else inquiries = inquiryService.getByItemname(pageable, start, end, itemname, chkAnswer);
//            }
//        } else if (itemname.equals("")) { // 유저아이디는 있고, 아이템 아이디는 없을 때
//            if (start.equals("") || end.equals("")){
//                if (chkAnswer == -1) inquiries = inquiryService.getByUsername(pageable, username);
//                else inquiries = inquiryService.getByUsername(pageable, username, chkAnswer);
//            } else {
//                if (chkAnswer == -1) inquiries = inquiryService.getByUsername(pageable, start, end, username);
//                else inquiries = inquiryService.getByUsername(pageable, start, end, username, chkAnswer);
//            }
//        } else { // 유저아이디 존재, 아이템 아이디도 존재할 때
//            if (start.equals("") || end.equals("")){
//                if (chkAnswer == -1) inquiries = inquiryService.getByUsernameAndItemname(pageable, username, itemname);
//                else inquiries = inquiryService.getByUsernameAndItemname(pageable, username, chkAnswer, itemname);
//            } else {
//                if (chkAnswer == -1) inquiries = inquiryService.getByUsernameAndItemname(pageable, start, end, username, itemname);
//                else inquiries = inquiryService.getByUsernameAndItemname(pageable, start, end, username, chkAnswer, itemname);
//            }
//        }
//
//        return ResponseEntity.ok().body(inquiries);
//    }
}
