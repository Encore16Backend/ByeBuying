package com.encore.byebuying.domain.inquiry;

import com.encore.byebuying.domain.inquiry.dto.InquiryAnswerDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class InquiryTest {

  @Autowired
  UserService userService;

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
    return new InquirySaveDTO("testInquiry", "testestestest", 1L, 1L);
  }

  @Test
  @Transactional
  public void 문의사항_등록() {
    // 유저 회원가입
    User user = userService.saveUser(createUser());
    log.info(">>> 회원가입 : {}", user);
    em.clear(); // 영속성 컨텍스트 초기화

    // 문의등록 Request
    InquirySaveDTO inquiryDTO = createInquiry();
    log.info(">>> Req : {}", inquiryDTO);

    // 문의등록
    Inquiry inquiry = inquiryService.saveInquiry(inquiryDTO);
    log.info("{}", inquiry);
    em.clear();

    User userInInquiry = inquiry.getUser();

    assertThat(userInInquiry).isNotNull();
    assertThat(userInInquiry.getId()).isEqualTo(user.getId());
    assertThat(userInInquiry.getUsername()).isEqualTo(user.getUsername());
    assertThat(userInInquiry.getPassword()).isEqualTo(user.getPassword());
    assertThat(userInInquiry.getEmail()).isEqualTo(user.getEmail());

    User findUser = userService.getUser(user.getUsername());
    Inquiry inquiryInUser = findUser.getInquiries().get(0);

    assertThat(inquiryInUser).isNotNull();
    assertThat(inquiryInUser.getTitle()).isEqualTo(inquiry.getTitle());
    assertThat(inquiryInUser.getContent()).isEqualTo(inquiry.getContent());
    assertThat(inquiryInUser.getAnswer()).isEqualTo(inquiry.getAnswer());
    assertThat(inquiryInUser.getChkanswer()).isEqualTo(inquiry.getChkanswer());
  }

  @Test
  @Transactional
  public void 문의사항_답변등록() {
    userService.saveUser(createUser());
    em.clear();

    InquirySaveDTO inquiryDTO = createInquiry();
    Inquiry saveInquiry = inquiryService.saveInquiry(inquiryDTO);
    Inquiry saveResult = inquiryService.getById(saveInquiry.getId());
    log.info(">>> create At: {}, update At: {}", saveResult.getCreatedAt(), saveResult.getModifiedAt());
    em.clear();

    // 문의사항 답변 Request
    InquiryAnswerDTO answerDTO = new InquiryAnswerDTO(saveInquiry.getId(), "testAnswer");
    log.info("Req : {}", answerDTO);

    // 답변 추가
    inquiryService.answerToInquiry(answerDTO);
    em.flush(); // flush 되는 시점에 변경된 컬럼이 있을 경우 update 쿼리 발생함
    em.clear();

    Inquiry inquiry = inquiryRepository.getById(answerDTO.getInquiryId());
    log.info(">>> {}", inquiry);
    log.info(">>> create At: {}, update At: {}", inquiry.getCreatedAt(), inquiry.getModifiedAt());
    assertThat(inquiry.getChkanswer()).isEqualTo(1);
    assertThat(inquiry.getAnswer()).isEqualTo(answerDTO.getAnswer());
  }
}