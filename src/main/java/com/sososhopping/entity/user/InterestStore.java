package com.sososhopping.entity.user;

import com.sososhopping.entity.owner.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestStore {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "interest_store_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public InterestStore(User user, Store store) {
        this.user = user;
        this.store = store;
    }
}