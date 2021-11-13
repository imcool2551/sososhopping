package com.sososhopping.server.entity.orders;

import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.store.Item;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Builder
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"order_id", "item_id"})
})
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(OrderItemId.class)
public class OrderItem extends BaseTimeEntity {

    @Id
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer totalPrice;

    // 연관 관계 편의 메서드
    public void setOrder(Order order) {
        this.order = order;
        this.order.getOrderItems().add(this);
    }
}
