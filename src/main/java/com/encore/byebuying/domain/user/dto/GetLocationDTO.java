package com.encore.byebuying.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetLocationDTO {

  private Long locationId;
  private boolean isDefaultLocation;

}
