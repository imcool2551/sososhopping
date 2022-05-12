package com.sososhopping.domain.store.service.user;

import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.domain.store.dto.user.response.InterestStoreResponse;
import com.sososhopping.domain.store.dto.user.response.StoreResponse;
import com.sososhopping.domain.store.repository.InterestStoreRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.InterestStore;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserStoreService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final InterestStoreRepository interestStoreRepository;
    private final UserPointRepository userPointRepository;


    public void toggleInterest(Long userId, Long storeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store does not exist with id " + storeId));

        interestStoreRepository.findByUserAndStore(user, store)
                .ifPresentOrElse(
                        interestStoreRepository::delete,
                        () -> interestStoreRepository.save(new InterestStore(user, store)));
    }

    public List<InterestStoreResponse> findInterestStores(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        return interestStoreRepository.findInterestStoresByUser(user)
                .stream()
                .map(InterestStore::getStore)
                .map(InterestStoreResponse::new)
                .collect(Collectors.toList());
    }

    public StoreResponse findStore(Long userId, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store does not exist with id " + storeId));

        if (userId == null) {
            return new StoreResponse(store, false, 0);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        boolean isInterestStore = interestStoreRepository.existsByStoreAndUser(store, user);
        int point = userPointRepository.findByUserAndStore(user, store)
                .map(UserPoint::getPoint)
                .orElse(0);
        return new StoreResponse(store, isInterestStore, point);
    }

}
