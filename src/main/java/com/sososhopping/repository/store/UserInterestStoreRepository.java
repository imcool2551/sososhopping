package com.sososhopping.repository.store;

import com.sososhopping.entity.user.InterestStore;

import java.util.List;

public interface UserInterestStoreRepository {

    List<InterestStore> findAllByUserId(Long userId);
}
