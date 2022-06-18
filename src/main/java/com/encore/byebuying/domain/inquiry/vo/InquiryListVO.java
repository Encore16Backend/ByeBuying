package com.encore.byebuying.domain.inquiry.vo;

import java.util.List;
import com.encore.byebuying.domain.inquiry.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryListVO {
  private List<Inquiry> inquiries;
}
