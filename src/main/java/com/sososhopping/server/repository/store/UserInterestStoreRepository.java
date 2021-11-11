package com.sososhopping.server.repository.store;

import com.sososhopping.server.entity.member.InterestStore;
import com.sososhopping.server.entity.member.User;

import java.util.List;

public interface UserInterestStoreRepository {

    List<InterestStore> findAllByUserId(Long userId);
}
