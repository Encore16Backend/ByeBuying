package com.encore.byebuying.domain.inquiry.dto;

import com.encore.byebuying.domain.inquiry.Inquiry;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InquiryDTO {
  @JsonProperty("inquiryId")
  private Long inquiry_id;
  private String username;
  private String title;
  private String content;
  private String answer;
  @JsonProperty("chkAnswer")
  private String chkAnswer;

  public InquiryDTO(Inquiry inquiry) {
    this.inquiry_id = inquiry.getId();
    this.username = inquiry.getUser().getUsername();
    this.title = inquiry.getTitle();
    this.content = inquiry.getContent();
    this.answer = inquiry.getAnswer();
    this.chkAnswer = inquiry.getChkAnswer().getDescription();
  }
}