package com.encore.byebuying.domain.inquiry;

import static org.assertj.core.api.Assertions.assertThat;

import com.encore.byebuying.domain.inquiry.controller.dto.SearchInquiryDTO;
import com.encore.byebuying.domain.inquiry.repository.InquiryRepository;
import com.encore.byebuying.domain.inquiry.service.vo.InquiryResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class InquiryRepositoryTest {

  @Autowired
  private InquiryRepository inquiryRepository;

  @Test
  void findAllTest() {
    Pageable pageable = PageRequest.of(0, 10);
    String username = "test";
    SearchInquiryDTO dto = new SearchInquiryDTO();
    dto.setUsername(username);
    Page<InquiryResponseVO> result = inquiryRepository.findAll(dto, pageable);

    assertThat(result).isNotNull();
  }
}
