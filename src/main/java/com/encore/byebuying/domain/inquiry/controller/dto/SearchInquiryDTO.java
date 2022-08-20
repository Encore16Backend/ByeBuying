package com.encore.byebuying.domain.inquiry.controller.dto;

import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchInquiryDTO extends PagingRequest {
  // 유저인 경우 username -> NULL
  // 관리자인 경우 NULL 일수도 있고 아닐수도 있음
  private String username;
}
