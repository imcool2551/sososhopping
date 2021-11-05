package com.sososhopping.server.repository.store;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sososhopping.server.entity.member.Review;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sososhopping.server.entity.member.QReview.review;
import static com.sososhopping.server.entity.member.QUser.user;


public class UserReviewRepositoryImpl implements UserReviewRepository {

    private final JPAQueryFactory queryFactory;

    public UserReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Review> findReviewsByStore(Long storeId) {
        return queryFactory
                .selectFrom(review)
                .join(review.user, user).fetchJoin()
                .where(storeIdEq(storeId))
                .fetch();
    }

    private BooleanExpression storeIdEq(Long storeId) {
        return review.store.id.eq(storeId);
    }
}
