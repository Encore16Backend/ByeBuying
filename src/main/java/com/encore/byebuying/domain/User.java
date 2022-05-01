package com.encore.byebuying.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id; // PK
    private String username; // Spring Security 의 User 에서 사용하는 필드명
    private String password; // 마찬가지
    private String email;

    @Embedded
    private Address address;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<OrderHistory> orderHistories = new ArrayList<>();

    private int defaultLocationIdx; // 기본 배송지 주소 id
    @OneToMany(cascade = CascadeType.ALL, fetch = LAZY)
    private Collection<Location> locations = new ArrayList<>(); // 배송지 목록


//    @ManyToMany(fetch = LAZY)
//    private Collection<Role> roles = new ArrayList<>(); // 권한

    @OneToOne(fetch = EAGER)
    private Role roles; // 권한
}
