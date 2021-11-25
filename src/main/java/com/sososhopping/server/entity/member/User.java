package com.sososhopping.server.entity.member;

import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.coupon.UserCoupon;
import com.sososhopping.server.entity.orders.Order;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(unique = true, columnDefinition = "char")
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

    @OneToMany(mappedBy = "user")
    private List<Cart> cart = new ArrayList<>();


    // Business Logic
    public void suspend() {
        active = AccountStatus.SUSPEND;
    }

    public void updateUserInfo(
            String name,
            String phone,
            String nickname,
            String streetAddress,
            String detailedAddress
    ) {
        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
