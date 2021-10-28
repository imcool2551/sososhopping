package com.sososhopping.server.entity.member.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sososhopping.server.entity.BaseTimeEntity;
import sosohagae.sososhop.entity.store.Store;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPointLog extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_point_log_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    private Integer pointAmount;

    @NotNull
    private Integer resultAmount;
}
