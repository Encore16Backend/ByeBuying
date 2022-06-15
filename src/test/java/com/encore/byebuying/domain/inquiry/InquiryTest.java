package com.encore.byebuying.domain.inquiry;

import java.util.List;
import com.encore.byebuying.ByebuyingApplication;
import com.encore.byebuying.domain.inquiry.dto.InquirySaveDTO;
import com.encore.byebuying.domain.inquiry.repository.InquiryRepository;
import com.encore.byebuying.domain.inquiry.service.InquiryService;
import com.encore.byebuying.domain.item.Item;
import com.encore.byebuying.domain.item.repository.ItemRepository;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.UserSaveDTO;
import com.encore.byebuying.domain.user.repository.UserRepository;
import com.encore.byebuying.domain.user.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@SpringBootTest
@Transactional
@Slf4j
class InquiryTest {

  @Autowired
  UserService userService;

  @Autowired
  InquiryService inquiryService;

  @Test
  public void 문의사항_등록() {
    // 유저 회원가입
    Collection<Location> locations = new ArrayList<>();
    locations.add(new Location(null, "testetesttest"));
    UserSaveDTO userDTO = new UserSaveDTO("test", "test123", "test@test.com", 0, locations);
    User user = userService.saveUser(userDTO);
    log.info(">>> 회원가입 : {}", user);

    // 문의등록 Request
    InquirySaveDTO inquiryDTO = new InquirySaveDTO("testInquiry", "testestestest", 1L, 1L);
    log.info(">>> Req : {}", inquiryDTO);

    // 문의등록
    Inquiry inquiry = inquiryService.saveInquiry(inquiryDTO);
    log.info("{}", inquiry);
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
}