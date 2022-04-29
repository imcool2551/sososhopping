package com.sososhopping.domain.store.repository;

import com.sososhopping.entity.user.InterestStore;
import com.sososhopping.entity.user.User;

import java.util.List;

public interface InterestStoreRepositoryCustom {

    List<InterestStore> findAllByUserId(Long userId);

    List<InterestStore> findInterestStoresByUser(User user);

}
