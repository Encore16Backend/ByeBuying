package com.encore.byebuying.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class Address {
    private String zipcode;
    private String address;
    private String detailAddress;
}
