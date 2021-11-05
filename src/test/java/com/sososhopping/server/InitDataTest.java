package com.sososhopping.server;

import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.coupon.FixCoupon;
import com.sososhopping.server.entity.coupon.RateCoupon;
import com.sososhopping.server.entity.member.AccountStatus;
import com.sososhopping.server.entity.member.Owner;
import com.sososhopping.server.entity.member.Review;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.*;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 테스트 데이터를 넣기 위한 파일입니다. @Rollback(false) 사용
 */
@SpringBootTest
@Transactional
class InitDataTest {

    @Autowired
    private EntityManager em;

    @Test
    @Rollback(false)
    void init() throws Exception {

        Store findStore = em.find(Store.class, 2L);
        Store findStore2 = em.find(Store.class, 32L);
        User findUser = em.find(User.class, 1L);
        User findUser2 = em.find(User.class, 4L);

        Review review1 = buildReview();
        Review review2 = buildReview();

        review2.setStore(findStore2);
        review2.setUser(findUser);
    }

    private User buildUser() {
        User user = User.builder()
                .email("test2@test.com")
                .password("password")
                .name("name")
                .phone("01043214321")
                .nickname("tester")
                .streetAddress("Seoul")
                .detailedAddress("A-102")
                .active(AccountStatus.ACTIVE)
                .build();
        return user;
    }

    private Review buildReview() {
        Review review = Review.builder()
                .content("후기입니다33. 정말 좋았습니다")
                .score(new BigDecimal(4.0))
                .build();
        return review;
    }
}