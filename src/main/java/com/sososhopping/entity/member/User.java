package com.sososhopping.entity.member;

import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.sososhopping.entity.member.AccountStatus.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String nickname;

    @Column(unique = true, columnDefinition = "char", length = 11)
    private String phone;

    private String streetAddress;

    private String detailedAddress;

    @Enumerated(EnumType.STRING)
    private AccountStatus active;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(String email,
                String password,
                String name,
                String nickname,
                String phone,
                String streetAddress,
                String detailedAddress,
                AccountStatus active) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.active = active;
    }

    // Business Logic
    public void suspend() {
        active = SUSPEND;
    }

    public void updateUserInfo(String name,
                               String phone,
                               String nickname,
                               String streetAddress,
                               String detailedAddress) {

        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public boolean withdrawable() {
        return orders.stream().noneMatch(order ->
            order.getOrderStatus() == OrderStatus.READY ||
            order.getOrderStatus() == OrderStatus.APPROVE ||
            order.getOrderStatus() == OrderStatus.PENDING
        );
    }

    public void withdraw() {
        active = WITHDRAW;
    }

    public boolean isActive() {
        return active == ACTIVE;
    }
}
