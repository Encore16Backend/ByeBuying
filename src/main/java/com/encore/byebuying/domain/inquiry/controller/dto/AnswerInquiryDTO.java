package com.encore.byebuying.domain.inquiry.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
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
public class AnswerInquiryDTO {
    @NotBlank
    @JsonProperty("inquiryId")
    private Long inquiry_id;
    @NotBlank
    private String answer;
}
