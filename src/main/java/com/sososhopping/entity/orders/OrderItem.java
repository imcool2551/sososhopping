package com.sososhopping.entity.orders;

import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.store.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"order_id", "item_id"})
})
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;

    private int totalPrice;

    public OrderItem(Order order, Item item, int quantity, int totalPrice) {
        this.order = order;
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
