package com.encore.byebuying.domain.inquiry.dto;

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
public class InquiryAnswerDTO {
    @NotBlank
    @JsonProperty("inquiryId")
    private Long inquiry_id;
    @NotBlank
    private String answer;
}
