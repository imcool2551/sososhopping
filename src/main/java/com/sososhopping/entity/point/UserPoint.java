package com.sososhopping.entity.point;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPoint extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_point_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private int point;

    @OneToMany(mappedBy = "userPoint", cascade = ALL, orphanRemoval = true)
    private List<UserPointLog> userPointLogs = new ArrayList<>();

    public UserPoint(User user, Store store, int point) {
        this.user = user;
        this.store = store;
        this.point = point;
    }

    public void updatePoint(int used) {
        int result = point + used;
        if (result < 0) {
            throw new BadRequestException("not enough point");
        }
        addLog(used, result);
        this.point = result;
    }

    private void addLog(int used, int result) {
        UserPointLog log = new UserPointLog(this, used, result);
        log.setUserPoint(this);
    }
}