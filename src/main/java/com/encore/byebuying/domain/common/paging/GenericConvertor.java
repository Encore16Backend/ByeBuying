package com.encore.byebuying.domain.common.paging;

import java.util.List;
import java.util.stream.Collectors;

public interface GenericConvertor<ENTITY, DTO> extends Convertor<ENTITY, DTO> {
  default List<DTO> convertToList(List<ENTITY> entities) {
    return entities.stream().map(this::convertor).collect(Collectors.toList());
  }
}
