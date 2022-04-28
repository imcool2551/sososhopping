package com.sososhopping.entity.point;

import com.sososhopping.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPointLog extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_point_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    })
    private UserPoint userPoint;

    private int pointAmount;

    private int resultAmount;

    @Builder
    public UserPointLog(int pointAmount, int resultAmount) {
        this.pointAmount = pointAmount;
        this.resultAmount = resultAmount;
    }

    // 연관 관계 편의 메서드
    public void setUserPoint(UserPoint userPoint) {
        this.userPoint = userPoint;
        this.userPoint.getUserPointLogs().add(this);
    }

}