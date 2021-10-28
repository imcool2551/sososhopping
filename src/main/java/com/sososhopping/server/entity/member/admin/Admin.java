package com.sososhopping.server.entity.member.admin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    @Id @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String nickname;

    @NotNull
    private String password;
}
