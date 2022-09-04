package com.encore.byebuying.domain.user.vo;

import com.encore.byebuying.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailVO extends UserDefaultVO {

  // TODO: 2022/09/05 갯수는 각각의 entity 서비스에서 개별적으로 조회하는 것이 좋을 듯 -> 이후 추가 개발
//  private BigDecimal orderCount;
//  private BigDecimal inquiryCount;
//  private BigDecimal reviewCount;

  // TODO: 2022/09/05 추가 회원 정보: 키, 몸무게, 환불 계좌, 성별, 생년월일, 소셜 로그인 연동 여부, 자기 소개, 수신 여부


  public UserDetailVO() {
  }

  public static UserDetailVO valueOf(User user) {
    UserDetailVO vo = new UserDetailVO();

    vo.setUserId(user.getId());
    vo.setUsername(user.getUsername());
    vo.setEmail(user.getEmail());

    vo.setRoleType(user.getRoleType());
    vo.setProviderType(user.getProvider());

    return vo;
  }

}
