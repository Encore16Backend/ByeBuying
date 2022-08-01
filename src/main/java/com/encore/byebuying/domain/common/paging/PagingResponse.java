package com.encore.byebuying.domain.common.paging;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PagingResponse<ENTITY, DTO extends GenericConvertor<ENTITY, DTO>> {
  private List<DTO> content;
  private int totalPage;

  public PagingResponse(DTO dto, Page<ENTITY> entities) {
    this.content = dto.convertToList(entities.getContent());
    this.totalPage = entities.getTotalPages();
  }
}
