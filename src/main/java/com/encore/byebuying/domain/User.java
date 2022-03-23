package com.encore.byebuying.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id; // PK
    private String username; // Spring Security의 User에서 사용하는 필드명
    private String password; // 마찬가지
    
    private int defaultLocationIdx; // 기본 배송지 주소 id
    @OneToMany(cascade = CascadeType.ALL, fetch = LAZY)
    private Collection<Location> locations = new ArrayList<>(); // 배송지 목록

    private String email;

//    @ManyToMany(fetch = LAZY)
//    private Collection<Role> roles = new ArrayList<>(); // 권한

    @OneToOne(fetch = EAGER)
    private Role roles; // 권한

}
