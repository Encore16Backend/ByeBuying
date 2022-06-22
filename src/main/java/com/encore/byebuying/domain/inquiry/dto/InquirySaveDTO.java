package com.encore.byebuying.domain.inquiry.dto;

import lombok.Data;

@Data
public class InquirySaveDTO {
    private String title;
    private String content;
    private Long item_id;
    private Long user_id;
}
