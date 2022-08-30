package com.encore.byebuying.domain.user.repository.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchLocationListParam {

  private Long userId;

  public static SearchLocationListParam valueOf(Long userId) {
    SearchLocationListParam param = new SearchLocationListParam();

    param.setUserId(userId);

    return param;
  }

}
