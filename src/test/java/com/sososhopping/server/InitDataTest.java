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
    void init() throws Exception {

        Store findStore = em.find(Store.class, 2L);

     }
}