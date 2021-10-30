package com.sososhopping.server.domain.entity.member;
import com.sososhopping.server.domain.entity.BaseTimeEntity;
import com.sososhopping.server.domain.entity.store.Store;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    @Column(unique = true, columnDefinition = "char")
    private String phone;

    @Enumerated(EnumType.STRING)
    private AccountStatus active;

    //List
    @OneToMany(mappedBy = "owner")
    private List<Store> stores;
}