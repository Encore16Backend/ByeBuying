package com.encore.byebuying.domain.user.repository;

import com.encore.byebuying.domain.user.repository.param.SearchLocationListParam;
import com.encore.byebuying.domain.user.vo.LocationVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationRepositoryCustom {

  Page<LocationVO> findAll(SearchLocationListParam param, Pageable pageable);

}
