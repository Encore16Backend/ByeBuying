package com.encore.byebuying.domain.inquiry.dto;

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
public class InquirySaveDTO {
    private String title;
    private String content;
    private Long user_id;
}
