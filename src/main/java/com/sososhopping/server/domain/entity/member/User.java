package com.sososhopping.server.domain.entity.member;

import com.sososhopping.server.domain.entity.BaseTimeEntity;
import com.sososhopping.server.domain.entity.InterestStore;
import com.sososhopping.server.domain.entity.Review;
import com.sososhopping.server.domain.entity.coupon.UserCoupon;
import com.sososhopping.server.domain.entity.orders.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    @Column(unique = true)
    private String phone;

    @NotNull
    @Column(name = "street_address")
    private String streetAddress;

    @NotNull
    @Column(name = "detailed_address")
    private String detailedAddress;

    @Enumerated(EnumType.STRING)
    private AccountStatus active;

    //List
    @OneToMany(mappedBy = "user")
    private List<UserPoint> userPoints = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<InterestStore> interestStores = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserCoupon> userCoupons = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}
