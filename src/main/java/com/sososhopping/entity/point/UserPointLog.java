package com.sososhopping.entity.point;

import com.sososhopping.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
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
    @JoinColumn(name = "user_point_id")
    private UserPoint userPoint;

    private int used;

    private int result;

    public UserPointLog(int used, int result) {
        this.used = used;
        this.result = result;
    }

    public void toUserPoint(UserPoint userPoint) {
        this.userPoint = userPoint;
        this.userPoint.getUserPointLogs().add(this);
    }

}