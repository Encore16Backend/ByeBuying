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



    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    private String providerId;

    public User(String username, String email, Role role, ProviderType providerType, String providerId) {
        this.username = username;
        this.password = "OAUTHLOGIN";
        this.defaultLocationIdx = 0;
        this.locations = new ArrayList<>();
        this.email = email;
        this.role = role;
        this.provider = providerType;
        this.providerId = providerId;
    }

    @Builder
    public User(String username, String password, String email, Collection<Location> locations, Role role, ProviderType providerType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.defaultLocationIdx = 0;
        this.locations = locations;
        this.role = role;
        this.provider = providerType;
    }

}
