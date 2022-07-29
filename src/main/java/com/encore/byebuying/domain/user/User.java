package com.encore.byebuying.domain.user;

import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.basket.Basket;
import com.encore.byebuying.domain.inquiry.Inquiry;
import com.encore.byebuying.domain.order.Order;
import com.encore.byebuying.domain.common.Address;
import com.encore.byebuying.domain.review.Review;
import com.encore.byebuying.domain.user.dto.UserDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(
    name = "Users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"username", "password", "email"}),
        @UniqueConstraint(columnNames = {"username", "address"})
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString(exclude = {"inquiries", "orders"})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id; // PK
    @Column(name = "username", nullable = false)
    private String username; // Spring Security 의 User 에서 사용하는 필드명
    @Column(name = "password", nullable = false)
    private String password; // 마찬가지
    @Column(name = "email", nullable = false)
    private String email;

    @Embedded
    @Column(name = "address", nullable = false)
    private Address address;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<Order> orders = new ArrayList<>();

    private int defaultLocationIdx; // 기본 배송지 주소 id
    @OneToMany(cascade = CascadeType.ALL, fetch = LAZY)
    private Collection<Location> locations = new ArrayList<>(); // 배송지 목록

    @OneToOne(fetch = LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private Basket basket;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = false)
    private ProviderType provider;
    @Column(name = "provider_id")
    private String providerId;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inquiry> inquiries = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Review> reviews = new ArrayList<>();

    // 일반
    @Builder(builderClassName = "init", builderMethodName = "initUser")
    private User(UserDTO dto, ProviderType provider) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.defaultLocationIdx = 0;
        this.locations = dto.getLocations();
        this.roleType = RoleType.USER;
        this.provider = provider;
        this.inquiries = new ArrayList<>();
        this.basket = Basket.createBasket();
        this.orders = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public void encodePassword(String password) {
        this.password = password;
    }

    public void changeUser(User user) {
        this.email = user.getEmail();
        this.defaultLocationIdx = user.getDefaultLocationIdx();
        this.locations = user.getLocations(); // 일단 그냥 이렇게 둠
    }
}
