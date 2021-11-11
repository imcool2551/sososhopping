package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.member.InterestStore;
import com.sososhopping.server.entity.member.InterestStoreId;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestStoreRepository extends
        JpaRepository<InterestStore, InterestStoreId>,
        UserInterestStoreRepository {

    Optional<InterestStore> findByUserAndStore(User user, Store store);

    boolean existsByStoreAndUser(Store store, User user);
}
