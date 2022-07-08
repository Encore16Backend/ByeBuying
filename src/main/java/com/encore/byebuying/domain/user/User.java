package com.encore.byebuying.domain.user;

import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.review.Review;
import com.encore.byebuying.domain.user.dto.UserSaveDTO;
import java.util.LinkedList;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Data
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"inquiries", "orders"})
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
    private List<Order> orders = new ArrayList<>();

    private int defaultLocationIdx; // 기본 배송지 주소 id
    @OneToMany(cascade = CascadeType.ALL, fetch = LAZY)
    private Collection<Location> locations = new ArrayList<>(); // 배송지 목록

    @OneToOne(fetch = LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    private String providerId;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inquiry> inquiries = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Review> reviews = new ArrayList<>();

    // OAuth
    public User(String username, String email, RoleType roleType, ProviderType providerType, String providerId) {
        this.username = username;
        this.password = "OAUTHLOGIN";
        this.defaultLocationIdx = 0;
        this.locations = new ArrayList<>();
        this.email = email;
        this.roleType = roleType;
        this.provider = providerType;
        this.providerId = providerId;
        this.inquiries = new ArrayList<>();
    }

    // 일반
    @Builder(builderClassName = "init", builderMethodName = "initUser")
    public User(UserSaveDTO dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.defaultLocationIdx = 0;
        this.locations = dto.getLocations();
        this.roleType = RoleType.USER;
        this.provider = ProviderType.LOCAL;
        this.inquiries = new ArrayList<>();
        this.basket = Basket.createBasket();
    }


}
