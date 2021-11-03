package com.sososhopping.server;

import com.sososhopping.server.entity.coupon.Coupon;
import com.sososhopping.server.entity.coupon.FixCoupon;
import com.sososhopping.server.entity.coupon.RateCoupon;
import com.sososhopping.server.entity.member.AccountStatus;
import com.sososhopping.server.entity.member.Owner;
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

        Coupon coupon = FixCoupon.builder()
                .storeName("김씨네 야채가게")
                .couponName("추석 할인 쿠폰")
                .stockQuantity(100)
                .couponCode("ABCDE12345")
                .minimumOrderPrice(10000)
                .startDate(LocalDateTime.now())
                .dueDate(LocalDateTime.of(2021, 11, 30, 0, 0, 0))
                .fixAmount(1000)
                .build();

        Coupon coupon2 = RateCoupon.builder()
                .storeName("김씨네 야채가게")
                .couponName("1주년 쿠폰")
                .stockQuantity(200)
                .couponCode("QWERT54321")
                .minimumOrderPrice(0)
                .startDate(LocalDateTime.now())
                .dueDate(LocalDateTime.of(2021, 11, 30, 0, 0, 0))
                .rateAmount(new BigDecimal(5.5))
                .build();

        coupon.setStore(findStore);
        coupon2.setStore(findStore);

        em.persist(findStore);
    }
}