package com.encore.byebuying.config.advice.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidInputVO {

  private String field;
  private Object value;
  private String details;

  public static InvalidInputVO valueOf(String field, Object value, String details) {
    InvalidInputVO vo = new InvalidInputVO();
    vo.setField(field);
    vo.setValue(value);
    vo.setDetails(details);
    return vo;
  }

}
