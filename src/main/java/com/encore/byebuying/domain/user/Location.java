package com.encore.byebuying.domain.user;

import static javax.persistence.GenerationType.IDENTITY;

import com.encore.byebuying.domain.user.dto.CreateLocationDTO;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_location_setting",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "default_location"})
  },
  indexes = {@Index(columnList = "user_id, default_location")}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Location {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  @Column(name = "location_name")
  private String name;
  @Column(name = "zip_code", nullable = false, length = 191)
  private String zipcode;
  @Column(name = "address", nullable = false,  length = 1000)
  private String address;
  @Column(name = "detail_address", nullable = false, length = 1000)
  private String detailAddress;
  @Column(name = "default_location", nullable = false)
  private boolean defaultLocation;
  @Column(name = "request_delivery_type", length = 1000)
  private String requestDeliveryType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Location)) {
      return false;
    }
    Location that = (Location) o;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public Location(String zipcode, String address, String detailAddress) {
    this.zipcode = zipcode;
    this.address = address;
    this.detailAddress = detailAddress;
  }

  public static Location createLocation() {
    return new Location();
  }

  public static Location createLocation(CreateLocationDTO dto, User user) {
    Location location = new Location();

    location.setName(dto.getName());
    location.setZipcode(dto.getZipcode());
    location.setAddress(dto.getAddress());
    location.setDetailAddress(dto.getDetailAddress());
    location.setDefaultLocation(dto.getDefaultLocation());
    location.setRequestDeliveryType(dto.getRequestDeliveryType());
    location.setUser(user);

    user.getLocations().add(location);

    return location;
  }


}
