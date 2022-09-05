package com.encore.byebuying.utils.response;

import com.encore.byebuying.config.advice.CommonException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {

  private final T data;
  private final String code;
  private final String message;

  public CommonResponse(ResponseCode responseCode) {
    this(null, responseCode);
  }

  public CommonResponse(CommonException e) {
    this(null, e.getResponseCode().getCode(), e.getMessage());
  }

  public CommonResponse(T data, ResponseCode responseCode) {
    this(data, responseCode.getCode(), responseCode.getMessage());
  }
}
