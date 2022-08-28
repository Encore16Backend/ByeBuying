package com.encore.byebuying.domain.inquiry.service;

import com.encore.byebuying.domain.code.InquiryType;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.common.service.UserServiceHelper;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.inquiry.controller.dto.AnswerInquiryDTO;
import com.encore.byebuying.domain.inquiry.controller.dto.SearchInquiryDTO;
import com.encore.byebuying.domain.inquiry.controller.dto.UpdateInquiryDTO;
import com.encore.byebuying.domain.inquiry.repository.InquiryRepository;
import com.encore.byebuying.domain.inquiry.repository.param.SearchInquiryListParam;
import com.encore.byebuying.domain.inquiry.service.vo.InquiryResponseVO;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;

    @Transactional
    public InquiryResponseVO updateInquiry(Long userId, UpdateInquiryDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException("User Entity Not Found"));

        Inquiry inquiry;
        if (dto.getInquiryId() != null) {
            // 수정 작업
            inquiry = inquiryRepository.findById(dto.getInquiryId())
                .orElseThrow(() -> new RuntimeException("Inquiry entity not found"));

            if (inquiry.getChkAnswer().equals(InquiryType.COMPLETE)) {
                throw new RuntimeException("Inquiry has already been answered");
            }

            // 권한 체크 - 문의사항 작성자 또는 관리자만 수정 가능
            userServiceHelper.checkAuthorityValidation(inquiry.getUser(), user);

            Inquiry.updateInquiry(inquiry, dto);
        } else {
            // 추가 작업
            inquiry = Inquiry.createInquiry(dto, user);
        }
        inquiryRepository.save(inquiry);

        return InquiryResponseVO.valueOf(inquiry);
    }

    @Transactional
    public InquiryResponseVO updateAnswerInquiry(long inquiryId, AnswerInquiryDTO dto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
            .orElseThrow(() -> new RuntimeException("Inquiry entity not found"));

        Inquiry.updateAnswerInquiry(inquiry, dto);
        inquiryRepository.save(inquiry);

        return InquiryResponseVO.valueOf(inquiry);
    }

    public InquiryResponseVO getInquiryDetail(Long userId, Long inquiryId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException("User Entity Not Found"));

        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(
            () -> new RuntimeException("Inquiry Entity Not Found"));

        // 권한 체크
        userServiceHelper.checkAuthorityValidation(inquiry.getUser(), user);

        return InquiryResponseVO.valueOf(inquiry);
    }

    public Page<InquiryResponseVO> getInquiries(Long userId, RoleType roleType, SearchInquiryDTO dto) {
        User user;

        if (RoleType.isAdmin(roleType)) {
            // 관리자
            user = userRepository.findByUsername(dto.getUsername()).orElse(null);
        } else {
            // 유저
            user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Entity Not Found"));
        }

        return inquiryRepository.findAll(SearchInquiryListParam.valueOf(user, dto),
            dto.getPageRequest());
    }

    @Transactional
    public void deleteInquiry(Long userId, Long inquiryId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException("User Entity Not Found"));

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
            .orElseThrow(() -> new RuntimeException("Inquiry entity not found"));
        // 권한 체크
        userServiceHelper.checkAuthorityValidation(inquiry.getUser(), user);
        inquiry.getUser().getInquiries().removeIf(item -> item == inquiry);
        inquiryRepository.delete(inquiry);
    }

//    @Override
//    public Page<Inquiry> getByItemid(Pageable pageable, Long item_id) {
//        return inquiryRepository.findByItemId(pageable, item_id);
//    }
//
//    @Override
//    public Page<Inquiry> getByItemName(Pageable pageable, String itemName) {
//        return inquiryRepository.findByItemName(pageable, itemName);
//    }
//
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
