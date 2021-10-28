package com.sososhopping.server.entity.report;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sosohagae.sososhop.entity.member.owner.Owner;
import sosohagae.sososhop.entity.member.users.User;
import sosohagae.sososhop.entity.store.Store;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReport {

    @Id
    @GeneratedValue
    @Column(name = "store_report_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String content;
}
