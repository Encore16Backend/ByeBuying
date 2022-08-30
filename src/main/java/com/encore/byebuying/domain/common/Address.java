package com.encore.byebuying.domain.common;

import com.encore.byebuying.domain.user.Location;
import lombok.*;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {
    private String zipcode;
    private String address;
    private String detailAddress;

    // 사용자가 설정해놓거나 새로 생성한 배송지 목록 중에서 선택한 경우
    public Address(Location location) {
        this.zipcode = location.getZipcode();
        this.address = location.getAddress();
        this.detailAddress = location.getDetailAddress();
    }

    // 배송지 목록에서 선택한 것이 아닌 주문하면서 새롭게 작성한 경우
    public Address(String zipcode, String address, String detailAddress) {
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
