package com.encore.byebuying.domain.inquiry.dto;

import com.encore.byebuying.domain.common.paging.GenericConvertor;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InquiryResponseDTO implements GenericConvertor<Inquiry, InquiryResponseDTO> {
  @JsonProperty("inquiryId")
  private Long inquiry_id;
  private String username;
  private String title;
  private String content;
  private String answer;
  @JsonProperty("chkAnswer")
  private String chkAnswer;

  public InquiryResponseDTO() {}

  public InquiryResponseDTO(Inquiry inquiry) {
    this.inquiry_id = inquiry.getId();
    this.username = inquiry.getUser().getUsername();
    this.title = inquiry.getTitle();
    this.content = inquiry.getContent();
    this.answer = inquiry.getAnswer();
    this.chkAnswer = inquiry.getChkAnswer().getDescription();
  }

  @Override
  public InquiryResponseDTO convertor(Inquiry inquiry) {
    return new InquiryResponseDTO(inquiry);
  }
}