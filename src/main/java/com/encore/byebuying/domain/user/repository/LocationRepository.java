package com.encore.byebuying.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.encore.byebuying.domain.user.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{
	Location findByLocation(String location);
}
