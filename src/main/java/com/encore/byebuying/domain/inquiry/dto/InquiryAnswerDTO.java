package com.encore.byebuying.domain.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
// for test
@ToString
@AllArgsConstructor
public class InquiryAnswerDTO {
    private Long inquiryId;
    private String answer;
}
