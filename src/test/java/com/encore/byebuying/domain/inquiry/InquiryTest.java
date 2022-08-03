package com.encore.byebuying.domain.inquiry;

import com.encore.byebuying.domain.code.InquiryType;
import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.inquiry.controller.dto.SearchInquiryDTO;
import com.encore.byebuying.domain.user.repository.UserRepository;
import com.encore.byebuying.domain.inquiry.controller.dto.UpdateInquiryDTO;
import com.encore.byebuying.domain.inquiry.repository.InquiryRepository;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.UserDTO;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
class InquiryTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private InquiryRepository inquiryRepository;
  @PersistenceContext
  private EntityManager em;

  private User user;
  private User admin;

  @BeforeEach
  public void setUp() {

    Collection<Location> userLocations = new ArrayList<>();
    userLocations.add(new Location(null, "testetesttest"));
    UserDTO userDTO = new UserDTO("test", "test123", "test@test.com", 0, userLocations);

    Collection<Location> adminLocations = new ArrayList<>();
    adminLocations.add(new Location(null, "adminadminadmin"));
    UserDTO adminDTO = new UserDTO("admin", "admin123", "admin@admin.com", 0, adminLocations);

    // 일반 유저 회원가입
    user = userRepository.save(User.initUser()
        .dto(userDTO)
        .provider(ProviderType.LOCAL).build());
    log.info(">>> 일반 유저 회원가입 : {}", user);

    // 관리자 회원가입
    admin = userRepository.save(User.initUser()
        .dto(adminDTO)
        .provider(ProviderType.LOCAL).build());
    admin.changeRoleTypeUser(RoleType.ADMIN);
    log.info(">>> 관리자 회원가입 : {}", admin);
    em.clear(); // 영속성 컨텍스트 초기화
    log.info(">>> BeforeEach Method End");
  }

  public UpdateInquiryDTO createInquiry() {
    return new UpdateInquiryDTO(null, "testInquiry", "testestestest", user.getUsername());
  }

  @Test
  void 문의사항_등록() {
    // 문의등록 Request
    UpdateInquiryDTO inquiryDTO = createInquiry();
    log.info(">>> Req : {}", inquiryDTO);

    // 문의등록
    Inquiry saveInquiry = Inquiry.updateInquiry(inquiryDTO, user);
    inquiryRepository.save(saveInquiry);
    log.info(">>> save Inquiry : {}", saveInquiry);
    em.flush();
    em.clear();

    Inquiry inquiry = inquiryRepository.findById(saveInquiry.getId())
        .orElseThrow(EntityNotFoundException::new);
    User userInInquiry = inquiry.getUser();

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
  void 문의사항_수정() {
    UpdateInquiryDTO inquiryDTO = createInquiry();
    Inquiry saveInquiry = Inquiry.updateInquiry(inquiryDTO, user);
    inquiryRepository.save(saveInquiry);
    em.flush();
    em.clear();

    log.info(">>> save : {}", saveInquiry);

    // 문의사항 수정
    UpdateInquiryDTO updateInquiryDTO =
        new UpdateInquiryDTO(saveInquiry.getId(), "updateInquiry", "updateupdate", saveInquiry.getUser().getUsername());
    Inquiry updateInquiry = inquiryRepository.findById(saveInquiry.getId())
        .orElseThrow(() -> new RuntimeException("inquiry not found"));
    updateInquiry.setTitle(updateInquiryDTO.getTitle());
    updateInquiry.setContent(updateInquiryDTO.getContent());
    em.flush();
    em.clear();

    log.info(">>> update after : {}", updateInquiry);

    assertThat(saveInquiry.getId()).isEqualTo(updateInquiry.getId());
    assertThat(saveInquiry.getTitle()).isNotEqualTo(updateInquiry.getTitle());
    assertThat(saveInquiry.getContent()).isNotEqualTo(updateInquiry.getTitle());
    assertThat(saveInquiry.getUser()).isEqualTo(updateInquiry.getUser());
  }

  @Test
  void 문의사항_답변등록() {
    UpdateInquiryDTO inquiryDTO = createInquiry();
    Inquiry saveInquiry = Inquiry.updateInquiry(inquiryDTO, user);
    inquiryRepository.save(saveInquiry);
    em.flush();
    em.clear();

    // 답변 추가
    Inquiry answerInquiry = inquiryRepository.findById(saveInquiry.getId())
        .orElseThrow(() -> new RuntimeException("Inquiry entity not found"));
    answerInquiry.setAnswer("answer");
    answerInquiry.setChkAnswer(InquiryType.COMPLETE);
    em.flush(); // flush 되는 시점에 변경된 컬럼이 있을 경우 update 쿼리 발생함
    em.clear();

    Inquiry inquiry = inquiryRepository.getById(saveInquiry.getId());
    log.info(">>> {}", inquiry);
    assertThat(inquiry.getChkAnswer()).isEqualTo(InquiryType.COMPLETE);
    assertThat(inquiry.getAnswer()).isEqualTo("answer");
  }

  @Test
  void 문의사항_불러오기() {
    UpdateInquiryDTO inquiryDTO;
    for (int i=0; i<8; i++) {
      inquiryDTO = createInquiry();
      inquiryDTO.setTitle((i+1) + ". " + inquiryDTO.getTitle());
      inquiryDTO.setContent((i+1) + ". " +inquiryDTO.getContent());
      Inquiry saveInquiry = Inquiry.updateInquiry(inquiryDTO, user);
      inquiryRepository.save(saveInquiry);
    }
    em.flush();
    em.clear();

    log.info("문의사항 전체 불러오기 TEST");
    SearchInquiryDTO dto = new SearchInquiryDTO();
    dto.setPageNumber(0);
    dto.setSize(10);

    var inquiries = inquiryRepository.findAll(dto, dto.getPageRequest());
    assertThat(inquiries.getContent().size()).isEqualTo(8);


    log.info("문의사항 한개 불러오기 TEST");
    var inquiry = inquiryRepository.getById(3L);
    assertThat(inquiry.getId()).isEqualTo(3L);
    assertThat(inquiry.getTitle()).isEqualTo("3. testInquiry");
    assertThat(inquiry.getContent()).isEqualTo("3. testestestest");

    log.info("문의사항 유저별 불러오기 TEST");
    dto.setUsername("test");
    var byUserInquiries = inquiryRepository.findAll(dto, dto.getPageRequest());
    var byUserInquiriesContent = byUserInquiries.getContent();
    for (int i=0; i<5; i++) {
      assertThat(byUserInquiriesContent.get(i).getUsername()).isEqualTo(user.getUsername());
    }
  }

  @Test
  void 문의사항_삭제() {
    UpdateInquiryDTO inquiryDTO = createInquiry();
    Inquiry saveInquiry = Inquiry.updateInquiry(inquiryDTO, user);
    inquiryRepository.save(saveInquiry);
    em.flush();
    em.clear();

    // 부모 엔티티에서 자식 엔티티와의 관계를 끊어야함
    user.getInquiries().removeIf(item -> item == saveInquiry);
    inquiryRepository.delete(saveInquiry);
    em.flush();
    em.clear();

    Inquiry inquiry = inquiryRepository.findById(saveInquiry.getId()).orElse(null);
    assertThat(inquiry).isNull();

    assertThat(user.getInquiries()).isEmpty();
  }
}