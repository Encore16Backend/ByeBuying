package com.encore.byebuying.domain.common.paging;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class PagingRequest {

  @Min(value = 0)
  private int pageNumber = 0;
  @Min(value = 1)
  @Max(value = 100)
  private int size = 12;

  public Pageable getPageRequest() {
    return PageRequest.of(this.pageNumber, this.size);
  }
}
