package com.encore.byebuying.domain.inquiry.dto;

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
public class InquirySaveDTO {
    @NotBlank
    private String title;
    @NotNull
    private String content;
    @NotBlank
    private String username;
}
