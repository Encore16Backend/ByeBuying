package com.encore.byebuying.domain.inquiry;

import com.encore.byebuying.domain.code.InquiryType;
import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.inquiry.service.vo.InquiryResponseVO;
import com.encore.byebuying.domain.user.repository.UserRepository;
import com.encore.byebuying.domain.inquiry.controller.dto.AnswerInquiryDTO;
import com.encore.byebuying.domain.inquiry.controller.dto.UpdateInquiryDTO;
import com.encore.byebuying.domain.inquiry.repository.InquiryRepository;
import com.encore.byebuying.domain.inquiry.service.InquiryService;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.UserDTO;
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

  public UserDTO createUser() {
    Collection<Location> locations = new ArrayList<>();
    locations.add(new Location(null, "testetesttest"));
    return new UserDTO("test", "test123", "test@test.com", 0, locations);
  }

  public UpdateInquiryDTO createInquiry(String username) {
    return new UpdateInquiryDTO("testInquiry", "testestestest", username);
  }

  @Test
  @Transactional
  public void 문의사항_등록() {
    // 유저 회원가입
    User user = userRepository.save(User.initUser()
        .dto(createUser())
        .provider(ProviderType.LOCAL).build());
    log.info(">>> 회원가입 : {}", user);
    em.clear(); // 영속성 컨텍스트 초기화

    // 문의등록 Request
    UpdateInquiryDTO inquiryDTO = createInquiry(user.getUsername());
    log.info(">>> Req : {}", inquiryDTO);

    // 문의등록
    InquiryResponseVO saveInquiry = inquiryService.saveInquiry(inquiryDTO);
    log.info(">>> save Inquiry : {}", saveInquiry);
    em.clear();

    Inquiry inquiry = inquiryRepository.findById(saveInquiry.getInquiryId())
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
    User user = userRepository.save(User.initUser()
        .dto(createUser())
        .provider(ProviderType.LOCAL).build());
    em.clear();

    UpdateInquiryDTO inquiryDTO = createInquiry(user.getUsername());
    InquiryResponseVO saveInquiry = inquiryService.saveInquiry(inquiryDTO);
    Inquiry saveResult = inquiryRepository.getById(saveInquiry.getInquiryId());
    log.info(">>> create At: {}, update At: {}", saveResult.getCreatedAt(), saveResult.getModifiedAt());
    em.clear();

    // 문의사항 답변 Request
    AnswerInquiryDTO answerDTO = new AnswerInquiryDTO(saveInquiry.getInquiryId(), "testAnswer");
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
    User user = userRepository.save(User.initUser()
        .dto(createUser())
        .provider(ProviderType.LOCAL).build());
    em.clear();

    UpdateInquiryDTO inquiryDTO;
    for (int i=0; i<8; i++) {
      inquiryDTO = createInquiry(user.getUsername());
      inquiryDTO.setTitle((i+1) + ". " + inquiryDTO.getTitle());
      inquiryDTO.setContent((i+1) + ". " +inquiryDTO.getContent());
      inquiryService.saveInquiry(inquiryDTO);
    }
    em.clear();

    // 문의사항 전체 불러오기 TEST
    PageRequest allInquiryPaging = PageRequest.of(0, 5);
    var allInquiries = inquiryService.getInquiries(allInquiryPaging);
    assertThat(allInquiries.getContent().size()).isEqualTo(5);

    allInquiryPaging = PageRequest.of(1, 5);
    allInquiries = inquiryService.getInquiries(allInquiryPaging);
    assertThat(allInquiries.getContent().size()).isEqualTo(3);

    // 문의사항 한개 불러오기 TEST
    var inquiry = inquiryService.getById(3L);
    assertThat(inquiry.getInquiryId()).isEqualTo(3L);
    assertThat(inquiry.getTitle()).isEqualTo("3. testInquiry");
    assertThat(inquiry.getContent()).isEqualTo("3. testestestest");

    // 문의사항 유저별 불러오기 TEST
    PageRequest byUserPaging = PageRequest.of(0, 5);
    User byUser = userRepository.getById(1L);
    var byUserInquiries = inquiryService.getByUser(byUserPaging, byUser.getUsername());
    var byUserInquiriesContent = byUserInquiries.getContent();
    for (int i=0; i<5; i++) {
      assertThat(byUserInquiriesContent.get(i).getUsername()).isEqualTo(byUser.getUsername());
    }
  }

  @Test
  @Transactional
  public void 문의사항_삭제() {
    User user = userRepository.save(User.initUser()
        .dto(createUser())
        .provider(ProviderType.LOCAL).build());
    em.clear();

    UpdateInquiryDTO inquiryDTO = createInquiry(user.getUsername());
    InquiryResponseVO saveInquiry = inquiryService.saveInquiry(inquiryDTO);
    Inquiry saveResult = inquiryRepository.getById(saveInquiry.getInquiryId());
    em.clear();

    Long inquiry_id = saveResult.getId();
    inquiryService.deleteInquiryById(inquiry_id);
    em.flush();
    em.clear();

    Inquiry inquiry = inquiryRepository.findById(inquiry_id).orElse(null);
    assertThat(inquiry).isNull();

    assertThat(user.getInquiries()).isEmpty();
  }
}