package com.sososhopping.entity.member;

import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import static javax.persistence.FetchType.*;

@Builder
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(ReviewId.class)
public class Review extends BaseTimeEntity {

    @Id
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Id
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String imgUrl;

    @NotNull
    private BigDecimal score;

    // 연관 관계 편의 메서드
    public void setStore(Store store) {
        this.store = store;
        this.store.getReviews().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        this.user.getReviews().add(this);
    }

    // Business Logic
    public void updateReview(String content, String imgUrl, BigDecimal score) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.score = score;
    }
}
