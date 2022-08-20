package com.encore.byebuying.domain.inquiry.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
// for Test
@AllArgsConstructor
@ToString
public class UpdateInquiryDTO {
    private Long inquiryId;
    @NotBlank
    private String title;
    @NotNull
    private String content;
}
