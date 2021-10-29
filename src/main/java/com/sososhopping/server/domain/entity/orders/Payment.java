package com.sososhopping.server.domain.entity.orders;

import com.sososhopping.server.domain.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment extends BaseTimeEntity {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @MapsId("orderId")
    @NotNull
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull
    private String pg;

    @NotNull
    private String payMethod;

    @NotNull
    private LocalDateTime payDatetime;

    @NotNull
    private Integer payAmount;

    @NotNull
    private String payIp;

    @NotNull
    @Column(unique = true)
    private String receiptUrl;

    @NotNull
    @Column(unique = true)
    private String payToken;

    private String payStatus;
}
