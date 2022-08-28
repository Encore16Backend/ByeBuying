package com.encore.byebuying.domain.inquiry.repository;

import com.encore.byebuying.domain.inquiry.repository.param.SearchInquiryListParam;
import com.encore.byebuying.domain.inquiry.service.vo.InquiryResponseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryRepositoryCustom {

  Page<InquiryResponseVO> findAll(SearchInquiryListParam param, Pageable pageable);
}
