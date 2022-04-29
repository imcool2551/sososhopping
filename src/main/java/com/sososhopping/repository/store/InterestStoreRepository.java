package com.sososhopping.repository.store;

import com.sososhopping.entity.user.InterestStore;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestStoreRepository extends JpaRepository<InterestStore, Long>, UserInterestStoreRepository {

    List<InterestStore> findByUser(User user);

    Optional<InterestStore> findByUserAndStore(User user, Store store);

    boolean existsByStoreAndUser(Store store, User user);
}
