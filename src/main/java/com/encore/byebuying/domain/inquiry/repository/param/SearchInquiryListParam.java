package com.encore.byebuying.domain.inquiry.repository.param;

import com.encore.byebuying.domain.inquiry.controller.dto.SearchInquiryDTO;
import com.encore.byebuying.domain.user.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class SearchInquiryListParam {

  private Long userId;

  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public static SearchInquiryListParam valueOf(@Nullable User user, SearchInquiryDTO dto) {
    SearchInquiryListParam param = new SearchInquiryListParam();

    if (user != null) {
      param.setUserId(user.getId());
    }

    param.setStartDate(dto.getStartDate());
    param.setEndDate(dto.getEndDate());

    return param;
  }
}
