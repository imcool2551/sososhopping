package com.sososhopping.server.service.user.store;

import com.sososhopping.server.common.dto.user.response.store.StoreListDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.member.InterestStore;
import com.sososhopping.server.entity.member.InterestStoreId;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.store.InterestStoreRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStoreService {

    private final InterestStoreRepository interestStoreRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void toggleStoreLike(Long userId, Long storeId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid token"));

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        interestStoreRepository
                .findByUserAndStore(user, store)
                .ifPresentOrElse(
                        existingStoreLike -> interestStoreRepository.delete(existingStoreLike),
                        () -> interestStoreRepository.save(new InterestStore(user, store))
                );
    }
}
