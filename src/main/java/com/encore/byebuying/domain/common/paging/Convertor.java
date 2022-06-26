package com.encore.byebuying.domain.common.paging;

public interface Convertor<ENTITY, DTO> {
  DTO convertor(ENTITY entity);
}
