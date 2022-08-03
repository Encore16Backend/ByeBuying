package com.encore.byebuying.domain.inquiry.controller.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerInquiryDTO {

    @NotBlank
    private String answer;
}
