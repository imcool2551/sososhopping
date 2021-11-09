package com.sososhopping.server.entity.member;

import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.store.Store;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(UserPointId.class)
public class UserPoint extends BaseTimeEntity {

    @Id @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    private Integer point;

    //List
    @OneToMany(mappedBy = "userPoint")
    private List<UserPointLog> userPointLogs = new ArrayList<>();

    public UserPoint(User user, Store store, Integer point) {
        this.user = user;
        this.store = store;
        this.point = point;
    }

    public void updatePoint(Integer pointAmount) {
        this.point += pointAmount;
    }
}