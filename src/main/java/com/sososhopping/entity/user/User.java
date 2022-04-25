package com.sososhopping.entity.user;

import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.common.AccountStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.sososhopping.entity.common.AccountStatus.ACTIVE;
import static com.sososhopping.entity.common.AccountStatus.SUSPEND;
import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Enumerated(STRING)
    private AccountStatus active;

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

    public boolean isActive() {
        return active == ACTIVE;
    }
}

