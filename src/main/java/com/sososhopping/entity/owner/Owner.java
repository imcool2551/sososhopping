package com.sososhopping.entity.owner;

import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.common.AccountStatus;
import com.sososhopping.entity.store.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "owner_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Column(unique = true, columnDefinition = "char", length = 11)
    private String phone;

    @Enumerated(STRING)
    private AccountStatus active;

    @Builder
    public Owner(String email,
                 String password,
                 String name,
                 String phone,
                 AccountStatus active) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.active = active;
    }

    public boolean isActive() {
        return active == AccountStatus.ACTIVE;
    }

    public void updateName(String name) {
        this.name = name;
    }
}