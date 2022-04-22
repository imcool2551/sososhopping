package com.sososhopping.entity.member;

import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class Owner extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true, columnDefinition = "char")
    private String phone;

    @Enumerated(EnumType.STRING)
    private AccountStatus active;


    //List
    @OneToMany(mappedBy = "owner")
    private List<Store> stores = new ArrayList<>();

    public boolean isActive() {
        return active == AccountStatus.ACTIVE;
    }

    public boolean credentialsMatch(String name, String phone) {
        return this.name.equals(name) && this.phone.equals(phone);
    }

    public boolean passwordMatch(String password) {
        return this.password.equals(password);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateInfo(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public void updatePassword(String newPassword) {
        password = newPassword;
    }
}