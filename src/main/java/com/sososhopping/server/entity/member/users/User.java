package com.sososhopping.server.entity.member.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.member.AccountStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private String streetAddress;

    @NotNull
    private String detailedAddress;

    @Enumerated(EnumType.STRING)
    private AccountStatus active;
}
