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

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserPointLog extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_point_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    })
    private UserPoint userPoint;

    @NotNull
    private Integer pointAmount;

    @NotNull
    private Integer resultAmount;
}