package com.encore.byebuying.domain.user.vo;

import com.encore.byebuying.domain.user.Location;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationVO {

  private Long id;
  private String name;
  private String zipcode;
  private String address;
  private String detailAddress;
  private boolean defaultLocation;
  private String requestDeliveryType;

  @QueryProjection
  public LocationVO(Long id, String name, String zipcode, String address, String detailAddress,
      boolean defaultLocation, String requestDeliveryType) {
    this.id = id;
    this.name = name;
    this.zipcode = zipcode;
    this.address = address;
    this.detailAddress = detailAddress;
    this.defaultLocation = defaultLocation;
    this.requestDeliveryType = requestDeliveryType;
  }

  public static LocationVO valueOf(Location location) {
    LocationVO vo = new LocationVO();

    vo.setId(location.getId());
    vo.setName(location.getName());
    vo.setZipcode(location.getZipcode());
    vo.setAddress(location.getAddress());
    vo.setDetailAddress(location.getDetailAddress());
    vo.setDefaultLocation(location.isDefaultLocation());
    vo.setRequestDeliveryType(location.getRequestDeliveryType());

    return vo;
  }

}
