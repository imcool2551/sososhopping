package com.sososhopping.server.domain.entity.report;

import com.sososhopping.server.domain.entity.BaseTimeEntity;
import com.sososhopping.server.domain.entity.member.User;
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
public class StoreReport extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "store_report_id")
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
    @Column(columnDefinition = "TEXT")
    private String content;
}