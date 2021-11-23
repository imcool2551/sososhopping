package com.sososhopping.server;

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

/**
 * 테스트 데이터를 넣기 위한 파일입니다. @Rollback(false) 사용
 */
@SpringBootTest
@Transactional
class InitDataTest {

    @Autowired
    private EntityManager em;

    @Test
    void init() throws Exception {
        for (int i = 0; i < 100; i++) {
            build6Stores();
        }
    }

    private void build6Stores() {
        Store store1 = buildStore(37.534473, 126.986634, "녹사평");
        Store store2 = buildStore(37.534518, 126.994392, "이태원");
        Store store3 = buildStore(37.539458, 127.001697, "한강진");
        Store store4 = buildStore(37.566160, 127.016255, "신당");
        Store store5 = buildStore(37.535400, 126.974039, "한강진");
        Store store6 = buildStore(37.519595, 126.988621, "서빙고");

        em.persist(store1);
        em.persist(store2);
        em.persist(store3);
        em.persist(store4);
        em.persist(store5);
        em.persist(store6);
    }

    private Store buildStore(double lat, double lng, String name) {
        Owner owner = em.find(Owner.class, 21L);
        return Store.builder()
                .owner(owner)
                .storeType(StoreType.BAKERY)
                .name(name)
                .phone("01012341234")
                .location(
                        new GeometryFactory().createPoint(new Coordinate(lng, lat))
                )
                .businessStatus(false)
                .storeStatus(StoreStatus.ACTIVE)
                .localCurrencyStatus(false)
                .pickupStatus(false)
                .deliveryStatus(false)
                .pointPolicyStatus(false)
                .streetAddress("Seoul")
                .detailedAddress("Seoul")
                .build();
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