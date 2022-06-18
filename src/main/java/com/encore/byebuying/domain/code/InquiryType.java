package com.encore.byebuying.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InquiryType {

  WAITING(0, "답변 대기"),
  COMPLETE(1, "답변 완료")
  ;

  private final int code;
  private final String description;
}
