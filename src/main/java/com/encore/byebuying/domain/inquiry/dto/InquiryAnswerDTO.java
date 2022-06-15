package com.encore.byebuying.domain.inquiry.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InquiryAnswerDTO {
    private Long inquiryId;
    private String answer;
}
