package com.sososhopping.repository.store;

import com.sososhopping.entity.member.InterestStore;
import com.sososhopping.entity.member.InterestStoreId;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestStoreRepository extends
        JpaRepository<InterestStore, InterestStoreId>,
        UserInterestStoreRepository {

    Optional<InterestStore> findByUserAndStore(User user, Store store);

    boolean existsByStoreAndUser(Store store, User user);
}
