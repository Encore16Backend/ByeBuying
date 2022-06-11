package com.encore.byebuying.api.dto.Inquiry;

import lombok.Data;

@Data
public class InquiryAnswerRequest {
    private Long inquiryId;
    private String answer;
}
