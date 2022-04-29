package com.sososhopping.domain.point.service;

import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.point.dto.response.MyPointsResponse;
import com.sososhopping.domain.point.dto.response.MyPointLogResponse;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.point.repository.UserPointLogRepository;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.point.UserPointLog;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.InterestStore;
import com.sososhopping.entity.user.User;
import com.sososhopping.domain.store.repository.InterestStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPointService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final UserPointRepository userPointRepository;
    private final UserPointLogRepository userPointLogRepository;
    private final InterestStoreRepository interestStoreRepository;

    public MyPointLogResponse findMonthlyPointLogs(Long userId, Long storeId, LocalDate yearMonth) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store does not exist with id" + storeId));

        UserPoint userPoint = userPointRepository.findByUserAndStore(user, store)
                .orElseThrow(() -> new NotFoundException("user does not have any point at store"));

        List<UserPointLog> userPointLogs = userPointLogRepository.findMonthlyPointLogs(userPoint, yearMonth);

        return new MyPointLogResponse(store, userPointLogs);
    }

    public MyPointsResponse findMyPoints(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        List<UserPoint> userPoints = userPointRepository.findByUser(user);
        List<Store> interestStores = interestStoreRepository.findByUser(user)
                .stream()
                .map(InterestStore::getStore)
                .collect(Collectors.toList());

        return new MyPointsResponse(userPoints, interestStores);
    }
}
