package com.encore.byebuying.domain.inquiry.service.vo;

import com.encore.byebuying.domain.common.paging.GenericConvertor;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InquiryResponseVO implements GenericConvertor<Inquiry, InquiryResponseVO> {
  private Long inquiryId;
  private String username;
  private String title;
  private String content;
  private String answer;
  private String chkAnswer;
  private LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;

  public InquiryResponseVO() {}

  public InquiryResponseVO(Inquiry inquiry) {
    this.inquiryId = inquiry.getId();
    this.username = inquiry.getUser().getUsername();
    this.title = inquiry.getTitle();
    this.content = inquiry.getContent();
    this.answer = inquiry.getAnswer();
    this.chkAnswer = inquiry.getChkAnswer().getDescription();
    this.createDateTime = inquiry.getCreatedAt();
    this.updateDateTime = inquiry.getModifiedAt();
  }

  @Override
  public InquiryResponseVO convertor(Inquiry inquiry) {
    return new InquiryResponseVO(inquiry);
  }
}