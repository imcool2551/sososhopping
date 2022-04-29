package com.sososhopping.entity.user;

import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "text")
    private String content;

    private String imgUrl;

    private BigDecimal score;

    @Builder
    public Review(Store store, User user, String content, String imgUrl, BigDecimal score) {
        this.store = store;
        this.user = user;
        this.content = content;
        this.imgUrl = imgUrl;
        this.score = score;
    }

}
