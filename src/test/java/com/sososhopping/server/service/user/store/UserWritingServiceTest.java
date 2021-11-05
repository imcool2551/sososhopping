package com.sososhopping.server.service.user.store;

import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.member.Owner;
import com.sososhopping.server.entity.store.*;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserWritingServiceTest {

    @Autowired EntityManager em;

    @Autowired UserWritingService userWritingService;

    @Test
    void 게시글_조회() {
        // given
        Owner owner = buildOwner();
        em.persist(owner);

        Store store = buildStore(owner);
        em.persist(store);

        Writing writing = buildWriting(store);
        em.persist(writing);

        // when
        Writing findWriting = userWritingService.getWriting(store.getId(), writing.getId());

        // then
        assertThat(findWriting).isEqualTo(writing);
    }

    @Test
    void 게시글_조회_404() {
        // given
        Owner owner = buildOwner();
        em.persist(owner);

        Store store = buildStore(owner);
        em.persist(store);

        Writing writing = buildWriting(store);
        em.persist(writing);

        Long randomId = 54321L;

        // when + then
        assertThrows(Api404Exception.class, () -> {
            userWritingService.getWriting(randomId, writing.getId());
        }, "존재하지 않는 점포입니다");

        assertThrows(Api404Exception.class, () -> {
            userWritingService.getWriting(store.getId(), randomId);
        }, "존재하지 않는 글입니다");
    }

    @Test
    void 게시글_조회_400() {
        // given
        Owner owner = buildOwner();
        em.persist(owner);

        Store store = buildStore(owner);
        em.persist(store);

        Store anotherStore = buildStore(owner);
        em.persist(anotherStore);

        Writing writing = buildWriting(store);
        em.persist(writing);

        // when + then
        assertThrows(Api400Exception.class, () -> {
            userWritingService.getWriting(anotherStore.getId(), writing.getId());
        });
    }

    private Owner buildOwner() {
        Owner owner = Owner.builder()
                .email("test@test.com")
                .password("password")
                .name("tester")
                .phone("01012341234")
                .build();

        return owner;
    }

    private Store buildStore(Owner owner) {
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(0.5, 0.5));

        Store store = Store.builder()
                .storeType(StoreType.VEGETABLES)
                .name("test shop")
                .phone("01012341234")
                .location(point)
                .businessStatus(true)
                .storeStatus(StoreStatus.ACTIVE)
                .localCurrencyStatus(true)
                .pickupStatus(true)
                .deliveryStatus(true)
                .pointPolicyStatus(true)
                .streetAddress("서울시")
                .detailedAddress("102호")
                .build();

        store.setOwner(owner);
        return store;
    }

    private Writing buildWriting(Store store) {
        Writing writing = Writing.builder()
                .title("제목2")
                .content("내용내용내용2")
                .writingType(WritingType.PROMOTION)
                .imgUrl("https://sample-img-url.com")
                .build();

        writing.setStore(store);
        return writing;
    }
}