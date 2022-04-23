package com.sososhopping.repository.store;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.entity.member.Review;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sososhopping.entity.member.QReview.review;
import static com.sososhopping.entity.store.QStore.store;
import static com.sososhopping.entity.user.QUser.user;


public class UserReviewRepositoryImpl implements UserReviewRepository {

    private final JPAQueryFactory queryFactory;

    public UserReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Review> findReviewsByStoreIdOrderByCreatedAtDesc(Long storeId) {
        return queryFactory
                .select(review)
                .from(review)
                .join(review.user, user).fetchJoin()
                .where(storeIdEq(storeId))
                .fetch();
    }

    @Override
    public List<Review> findReviewsByUserId(Long userId) {
        return queryFactory
                .select(review)
                .from(review)
                .join(review.store, store).fetchJoin()
                .where(userIdEq(userId))
                .fetch();
    }

    private BooleanExpression userIdEq(Long userId) {
        return review.user.id.eq(userId);
    }

    private BooleanExpression storeIdEq(Long storeId) {
        return review.store.id.eq(storeId);
    }
}
