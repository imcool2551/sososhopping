package com.sososhopping.entity.member;

import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.sososhopping.entity.member.AccountStatus.*;

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

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    // Business Logic
    public void suspend() {
        active = SUSPEND;
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
