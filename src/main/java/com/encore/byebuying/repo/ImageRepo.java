package com.encore.byebuying.repo;

import com.encore.byebuying.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long> {
    Image findByImgid(Long name);
}
