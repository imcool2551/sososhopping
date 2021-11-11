package com.sososhopping.server.entity.member;

import com.sososhopping.server.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Builder
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserPointLog extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // 연관 관계 편의 메서드
    public void setUserPoint(UserPoint userPoint) {
        this.userPoint = userPoint;
        this.userPoint.getUserPointLogs().add(this);
    }
}