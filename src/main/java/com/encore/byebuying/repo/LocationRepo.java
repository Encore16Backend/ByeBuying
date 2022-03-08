package com.encore.byebuying.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encore.byebuying.domain.Location;

public interface LocationRepo extends JpaRepository<Location, Long>{
	Location findByLocation(String location);
}
