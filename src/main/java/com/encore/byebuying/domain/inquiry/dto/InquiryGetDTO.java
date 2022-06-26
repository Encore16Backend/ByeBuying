package com.encore.byebuying.domain.inquiry.dto;

import com.encore.byebuying.domain.common.paging.PagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InquiryGetDTO extends PagingRequest {

  private String username;
}
