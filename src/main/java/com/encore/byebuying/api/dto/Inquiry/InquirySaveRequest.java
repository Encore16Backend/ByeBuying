package com.encore.byebuying.api.dto.Inquiry;

import lombok.Data;

@Data
public class InquirySaveRequest {
    private String title;
    private String content;
    private Long item_id;
    private Long user_id;
}
