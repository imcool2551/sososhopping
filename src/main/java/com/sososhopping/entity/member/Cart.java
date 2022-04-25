package com.sososhopping.entity.member;

import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;

    @Builder
    public Cart(User user, Item item, Integer quantity) {
        this.user = user;
        this.item = item;
        this.quantity = quantity;
    }

    // Business Logic
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}
