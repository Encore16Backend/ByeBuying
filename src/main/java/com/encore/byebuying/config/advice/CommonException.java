package com.encore.byebuying.config.advice;

import com.encore.byebuying.utils.ResponseCode;
import lombok.Getter;

@Getter
public abstract class CommonException extends RuntimeException {

  private ResponseCode responseCode;

  public CommonException(ResponseCode responseCode) {
    super(responseCode.getMessage());
    this.responseCode = responseCode;
  }

  public CommonException(ResponseCode responseCode, String detailMessage) {
    super(responseCode.getMessage() + " : " + detailMessage);
    this.responseCode = responseCode;
  }

}
