package com.encore.byebuying.domain.user.repository;

import com.encore.byebuying.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.encore.byebuying.domain.user.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {

  Optional<Location> findByIdAndUser(long locationId, User user);
}
