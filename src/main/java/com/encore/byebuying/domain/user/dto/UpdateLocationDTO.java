package com.encore.byebuying.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UpdateLocationDTO {

  private Long locationId;

  @Length(max = 255)
  private String name;

  @NotBlank
  @Length(max = 100)
  private String zipcode;
  @NotBlank
  @Length(max = 1000)
  private String address;
  @NotBlank
  @Length(max = 1000)
  private String detailAddress;

  @NotNull
  private Boolean defaultLocation;
  @Length(max = 2000)
  private String requestDeliveryType;

}
