package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.owner.Store;
import com.sososhopping.entity.owner.StoreStatus;
import com.sososhopping.repository.store.UserStoreRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, UserStoreRepository {

    @EntityGraph(attributePaths = {"storeMetadata"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Store> findByStoreStatus(StoreStatus storeStatus);

    //점주 가게 정보 read with 사업자 정보
    @EntityGraph(attributePaths = {"storeBusinessDays"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Store> findStoreInforById(Long storeId);

    //점주 가게 정보 read with 물품
    @EntityGraph(attributePaths = {"items"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Store> findStoreWithItemById(Long storeId);

    //점주 가게 정보 read with 글
    @EntityGraph(attributePaths = {"writings"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Store> findStoreWithWritingById(Long storeId);

    List<Store> findByIdIn(List<Long> id);
}
