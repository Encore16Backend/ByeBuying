package com.encore.byebuying.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id; // PK
    private String username; // Spring Security의 User에서 사용하는 필드명
    private String password; // 마찬가지
    private String location;
    private int style;
    private String email;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>(); // 권한

}
