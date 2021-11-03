package com.sososhopping.server;

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

/**
 * 테스트 데이터를 넣기 위한 파일입니다. @Rollback(false) 사용
 */
@SpringBootTest
@Transactional
class InitDataTest {

    @Autowired
    private EntityManager em;

    @Test
    void test() throws Exception {
        Owner owner = Owner.builder()
                .email("test2@test.com")
                .password("password")
                .name("ownerA")
                .phone("01012345678")
                .active(AccountStatus.ACTIVE)
                .build();

        em.persist(owner);

        GeometryFactory gf = new GeometryFactory();

        Store store = Store.builder()
                .name("김씨네 야채가게")
                .storeType(StoreType.VEGETABLES)
                .imgUrl("대표이미지 URL")
                .description("싱싱한 야채를 팝니다")
                .extraBusinessDay("매월 2주, 4주 수요일은 휴무일입니다")
                .phone("01012345678")
                .location(gf.createPoint(new Coordinate(0, 0)))
                .businessStatus(true)
                .storeStatus(StoreStatus.ACTIVE)
                .localCurrencyStatus(true)
                .pickupStatus(true)
                .deliveryStatus(true)
                .pointPolicyStatus(true)
                .streetAddress("서울시 용산구 녹사평대로 40다길 19")
                .detailedAddress("A동 101호")
                .build();

        store.setOwner(owner);

        em.persist(store);
        em.flush();
        em.clear();
     }

     @Test
     void test2() throws Exception {

         StoreImage image = StoreImage.builder()
                 .imgUrl("https://sample-image-url.com")
                 .build();

         Store findStore = em.find(Store.class, 2L);

         image.setStore(findStore);

         em.persist(findStore);
     }
}