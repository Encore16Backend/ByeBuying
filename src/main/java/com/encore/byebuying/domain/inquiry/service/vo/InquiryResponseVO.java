package com.encore.byebuying.domain.inquiry.service.vo;

import com.encore.byebuying.domain.code.InquiryType;
import com.encore.byebuying.domain.common.paging.GenericConvertor;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InquiryResponseVO {
  private Long inquiryId;
  private String title;
  private String content;
  private String answer;
  private InquiryType chkAnswer;
  private LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;

  private String username;

  public InquiryResponseVO() {}

  @QueryProjection
  public InquiryResponseVO(Long inquiryId, String title, String content, String answer,
      InquiryType chkAnswer, LocalDateTime createDateTime, LocalDateTime updateDateTime,
      String username) {
    this.inquiryId = inquiryId;
    this.title = title;
    this.content = content;
    this.answer = answer;
    this.chkAnswer = chkAnswer;
    this.createDateTime = createDateTime;
    this.updateDateTime = updateDateTime;
    this.username = username;
  }

  public static InquiryResponseVO valueOf(Inquiry inquiry) {
    InquiryResponseVO vo = new InquiryResponseVO();
    vo.setInquiryId(inquiry.getId());
    vo.setTitle(inquiry.getTitle());
    vo.setContent(inquiry.getContent());
    vo.setAnswer(inquiry.getAnswer());
    vo.setCreateDateTime(inquiry.getCreatedAt());
    vo.setUpdateDateTime(inquiry.getModifiedAt());
    vo.setChkAnswer(inquiry.getChkAnswer());

    User user = inquiry.getUser();
    vo.setUsername(user.getUsername());
    return vo;
  }
}