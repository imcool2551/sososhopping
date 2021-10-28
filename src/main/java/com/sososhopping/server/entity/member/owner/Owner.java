package com.sososhopping.server.entity.member.owner;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.member.AccountStatus;
import sosohagae.sososhop.entity.store.Store;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner extends BaseTimeEntity {

    @Id @GeneratedValue
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
    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private AccountStatus active;

    @OneToMany(mappedBy = "owner")
    private List<Store> stores;
}
