package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.owner.Owner;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.StoreStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @EntityGraph(attributePaths = {"storeMetadata"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Store> findByStoreStatus(StoreStatus storeStatus);

    List<Store> findByOwner(Owner owner);

    @EntityGraph(attributePaths = {"writings"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Store> findStoreWithWritingById(Long storeId);

    List<Store> findByIdIn(List<Long> id);
}
