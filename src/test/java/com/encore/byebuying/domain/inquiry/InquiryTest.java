package com.encore.byebuying.domain.inquiry;

import com.encore.byebuying.domain.code.InquiryType;
import com.encore.byebuying.domain.inquiry.dto.InquiryDTO;
import com.encore.byebuying.domain.user.repository.UserRepository;
import java.util.List;
import com.encore.byebuying.domain.inquiry.dto.InquiryAnswerDTO;
import com.encore.byebuying.domain.inquiry.dto.InquiryListDTO;
import com.encore.byebuying.domain.inquiry.dto.InquirySaveDTO;
import com.encore.byebuying.domain.inquiry.repository.InquiryRepository;
import com.encore.byebuying.domain.inquiry.service.InquiryService;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.UserSaveDTO;
import com.encore.byebuying.domain.user.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@SpringBootTest
@Slf4j
class InquiryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  InquiryService inquiryService;

  @Autowired
  InquiryRepository inquiryRepository;

  @Autowired
  EntityManager em;

  public UserSaveDTO createUser() {
    Collection<Location> locations = new ArrayList<>();
    locations.add(new Location(null, "testetesttest"));
    return new UserSaveDTO("test", "test123", "test@test.com", 0, locations);
  }

  public InquirySaveDTO createInquiry() {
    return new InquirySaveDTO("testInquiry", "testestestest", 1L);
  }

  @Test
  @Transactional
  public void 문의사항_등록() {
    // 유저 회원가입
    User user = userRepository.save(new User(createUser()));
    log.info(">>> 회원가입 : {}", user);
    em.clear(); // 영속성 컨텍스트 초기화

    // 문의등록 Request
    InquirySaveDTO inquiryDTO = createInquiry();
    log.info(">>> Req : {}", inquiryDTO);

    // 문의등록
    InquiryDTO saveInquiry = inquiryService.saveInquiry(inquiryDTO);
    log.info(">>> save Inquiry : {}", saveInquiry);
    em.clear();

    Inquiry inquiry = inquiryRepository.findById(saveInquiry.getInquiry_id())
        .orElseThrow(EntityNotFoundException::new);
    User userInInquiry = userRepository.findById(inquiry.getUser().getId())
        .orElseThrow(EntityNotFoundException::new);

    assertThat(userInInquiry).isNotNull();
    assertThat(userInInquiry.getId()).isEqualTo(user.getId());
    assertThat(userInInquiry.getUsername()).isEqualTo(user.getUsername());
    assertThat(userInInquiry.getPassword()).isEqualTo(user.getPassword());
    assertThat(userInInquiry.getEmail()).isEqualTo(user.getEmail());

    User findUser = userRepository.findById(user.getId())
        .orElseThrow(EntityNotFoundException::new);
    Inquiry inquiryInUser = findUser.getInquiries().get(0);

    assertThat(inquiryInUser).isNotNull();
    assertThat(inquiryInUser.getTitle()).isEqualTo(inquiry.getTitle());
    assertThat(inquiryInUser.getContent()).isEqualTo(inquiry.getContent());
    assertThat(inquiryInUser.getAnswer()).isEqualTo(inquiry.getAnswer());
    assertThat(inquiryInUser.getChkAnswer()).isEqualTo(inquiry.getChkAnswer());
  }

  @Test
  @Transactional
  public void 문의사항_답변등록() {
    userRepository.save(new User(createUser()));
    em.clear();

    InquirySaveDTO inquiryDTO = createInquiry();
    InquiryDTO saveInquiry = inquiryService.saveInquiry(inquiryDTO);
    Inquiry saveResult = inquiryRepository.getById(saveInquiry.getInquiry_id());
    log.info(">>> create At: {}, update At: {}", saveResult.getCreatedAt(), saveResult.getModifiedAt());
    em.clear();

    // 문의사항 답변 Request
    InquiryAnswerDTO answerDTO = new InquiryAnswerDTO(saveInquiry.getInquiry_id(), "testAnswer");
    log.info("Req : {}", answerDTO);

    // 답변 추가
    inquiryService.answerToInquiry(answerDTO);
    em.flush(); // flush 되는 시점에 변경된 컬럼이 있을 경우 update 쿼리 발생함
    em.clear();

    Inquiry inquiry = inquiryRepository.getById(answerDTO.getInquiry_id());
    log.info(">>> {}", inquiry);
    log.info(">>> create At: {}, update At: {}", inquiry.getCreatedAt(), inquiry.getModifiedAt());
    assertThat(inquiry.getChkAnswer()).isEqualTo(InquiryType.COMPLETE);
    assertThat(inquiry.getAnswer()).isEqualTo(answerDTO.getAnswer());
  }

  @Test
  @Transactional(readOnly = true)
  public void 문의사항_불러오기() {
    userRepository.save(new User(createUser()));
    em.clear();

    InquirySaveDTO inquiryDTO;
    for (int i=0; i<8; i++) {
      inquiryDTO = createInquiry();
      inquiryDTO.setTitle((i+1) + ". " + inquiryDTO.getTitle());
      inquiryDTO.setContent((i+1) + ". " +inquiryDTO.getContent());
      inquiryService.saveInquiry(inquiryDTO);
    }
    em.clear();

    // 문의사항 전체 불러오기 TEST
    PageRequest allInquiryPaging = PageRequest.of(0, 5);
    InquiryListDTO allInquiries = inquiryService.getInquiries(allInquiryPaging);
    assertThat(allInquiries.getInquiries().size()).isEqualTo(5);

    allInquiryPaging = PageRequest.of(1, 5);
    allInquiries = inquiryService.getInquiries(allInquiryPaging);
    assertThat(allInquiries.getInquiries().size()).isEqualTo(3);

    // 문의사항 한개 불러오기 TEST
    InquiryDTO inquiry = inquiryService.getById(3L);
    assertThat(inquiry.getInquiry_id()).isEqualTo(3L);
    assertThat(inquiry.getTitle()).isEqualTo("3. testInquiry");
    assertThat(inquiry.getContent()).isEqualTo("3. testestestest");

    // 문의사항 유저별 불러오기 TEST
    PageRequest byUserPaging = PageRequest.of(0, 5);
    User user = userRepository.getById(1L);
    InquiryListDTO byUserInquiries = inquiryService.getByUserId(byUserPaging, user.getId());
    for (int i=0; i<5; i++) {
      assertThat(byUserInquiries.getInquiries().get(i).getUsername()).isEqualTo(user.getUsername());
    }
  }

  @Test
  @Transactional
  public void 문의사항_삭제() {
    userRepository.save(new User(createUser()));
    em.clear();

    InquirySaveDTO inquiryDTO = createInquiry();
    InquiryDTO saveInquiry = inquiryService.saveInquiry(inquiryDTO);
    Inquiry saveResult = inquiryRepository.getById(saveInquiry.getInquiry_id());
    em.clear();

    Long inquiry_id = saveResult.getId();
    inquiryService.deleteInquiryById(inquiry_id);
    em.flush();
    em.clear();

    Inquiry inquiry = inquiryRepository.findById(inquiry_id).orElse(null);
    assertThat(inquiry).isNull();

    User user = userRepository.getById(1L);
    assertThat(user.getInquiries()).isEmpty();
  }
}