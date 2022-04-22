package com.sososhopping.repository.store;

import com.sososhopping.entity.member.InterestStore;

import java.util.List;

public interface UserInterestStoreRepository {

    List<InterestStore> findAllByUserId(Long userId);
}
