package com.sososhopping.entity.user;

import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.store.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPoint extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_point_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private Integer point;

    //List
    @OneToMany(mappedBy = "userPoint", cascade = ALL, orphanRemoval = true)
    private List<UserPointLog> userPointLogs = new ArrayList<>();

    public UserPoint(User user, Store store, Integer point) {
        this.user = user;
        this.store = store;
        this.point = point;
    }

    public void updatePoint(Integer pointAmount) {
        this.point += pointAmount;
    }

    public boolean hasMoreThan(Integer usedPoint) {
        return point >= usedPoint;
    }

    public void usePoint(Integer usedPoint) {
        point -= usedPoint;

        UserPointLog userPointLog = UserPointLog.builder()
                .pointAmount(-usedPoint)
                .resultAmount(point)
                .build();

        userPointLog.setUserPoint(this);
    }

    public void savePoint(Order order) {
        Integer finalPrice = order.getFinalPrice();
        BigDecimal saveRate = order.getStore().getSaveRate();

        if (saveRate != null) {
            int savedPoint = (int)(finalPrice * saveRate.doubleValue() / 100);

            point += savedPoint;

            UserPointLog userPointLog = UserPointLog.builder()
                    .pointAmount(savedPoint)
                    .resultAmount(point)
                    .build();

            userPointLog.setUserPoint(this);
        }
    }

    public void restorePoint(Order order) {

        Integer restorePoint = order.getUsedPoint();

        if (restorePoint > 0) {
            point += restorePoint;

            UserPointLog userPointLog = UserPointLog.builder()
                    .pointAmount(restorePoint)
                    .resultAmount(point)
                    .build();

            userPointLog.setUserPoint(this);
        }
    }
}