package com.encore.byebuying.domain.inquiry.dto;

import com.encore.byebuying.domain.inquiry.Inquiry;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class InquiryListDTO {
  private List<InquiryDTO> inquiries;

  public InquiryListDTO(List<Inquiry> inquiries) {
    this.inquiries = inquiries.stream()
        .map(InquiryDTO::new).collect(Collectors.toList());
  }
}