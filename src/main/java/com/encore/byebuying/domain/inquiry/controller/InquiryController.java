package com.encore.byebuying.domain.inquiry.controller;

import com.encore.byebuying.config.auth.LoginUser;
import com.encore.byebuying.domain.common.paging.PagingResponse;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.controller.dto.AnswerInquiryDTO;
import com.encore.byebuying.domain.inquiry.controller.dto.SearchInquiryDTO;
import com.encore.byebuying.domain.inquiry.service.InquiryService;
import com.encore.byebuying.domain.inquiry.service.vo.InquiryResponseVO;
import com.encore.byebuying.domain.inquiry.controller.dto.UpdateInquiryDTO;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;

    // 문의사항 등록 및 수정
    @PostMapping
    public ResponseEntity<?> updateInquiry(@AuthenticationPrincipal LoginUser loginUser,
        @Valid @RequestBody UpdateInquiryDTO dto){
        InquiryResponseVO inquiryResponseVO = inquiryService
            .updateInquiry(loginUser.getUserId(), dto);
        return new ResponseEntity<>(inquiryResponseVO, HttpStatus.OK);
    }

    // 문의사항 답변 등록 - 관리자
    @PostMapping("/sy/{id}/answer")
    public ResponseEntity<?> answerToInquiry(@PathVariable(value = "id") long inquiryId,
        @Valid @RequestBody AnswerInquiryDTO dto){
        InquiryResponseVO inquiryResponseVO = inquiryService.answerToInquiry(inquiryId, dto);
        return new ResponseEntity<>(inquiryResponseVO, HttpStatus.OK);
    }

    // 문의사항 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getInquiryDetail(
        @RequestParam(value = "username") String username,
        @PathVariable(value = "id") Long inquiryId){
        InquiryResponseVO inquiryResponseVO = inquiryService.getInquiryDetail(username, inquiryId);
        return new ResponseEntity<>(inquiryResponseVO, HttpStatus.OK);
    }

    // 문의사항 목록 불러오기
    @GetMapping
    public ResponseEntity<?> getInquiries(SearchInquiryDTO dto) {
        Page<InquiryResponseVO> inquiries = inquiryService.getInquiries(dto, dto.getPageRequest());
        return new ResponseEntity<>(inquiries, HttpStatus.OK);
    }

    // 문의사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInquiry(
        @AuthenticationPrincipal LoginUser loginUser,
        @PathVariable(value = "id") Long inquiryId){
        inquiryService.deleteInquiry(loginUser.getUserId(), inquiryId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
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
