package com.encore.byebuying.domain.user.repository.user;

import com.encore.byebuying.domain.user.repository.user.param.SearchUserListParam;
import com.encore.byebuying.domain.user.vo.UserListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

  Page<UserListVO> findAll(SearchUserListParam param, Pageable pageable);

}
