package com.sososhopping.server.repository.store;

import com.sososhopping.server.common.dto.user.response.store.StoreListDto;
import com.sososhopping.server.entity.member.Owner;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.StoreStatus;
import com.sososhopping.server.entity.store.StoreType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, UserStoreRepository {

    List<Store> findByOwner(Owner owner);

    @EntityGraph(attributePaths = {"storeMetaData"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Store> findByStoreStatus(StoreStatus storeStatus);

    //점주 가게 정보 read with 사업자 정보
    @EntityGraph(attributePaths = {"storeBusinessDays"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Store> findStoreInforById(Long storeId);

    @EntityGraph(attributePaths = {"reviews"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Store> findStoreDetailById(Long storeId);
    
    //점주 가게 정보 read with 물품
    @EntityGraph(attributePaths = {"items"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Store> findStoreWithItemById(Long storeId);

    //점주 가게 정보 read with 글
    @EntityGraph(attributePaths = {"writings"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Store> findStoreWithWritingById(Long storeId);

    List<Store> findByStoreType(StoreType storeType);

    List<Store> findByIdIn(List<Long> id);
}
