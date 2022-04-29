package com.sososhopping.domain.point.service;

import com.sososhopping.domain.point.dto.response.UserPointLogResponse;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.point.repository.UserPointLogRepository;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.point.UserPointLog;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPointService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final UserPointRepository userPointRepository;
    private final UserPointLogRepository userPointLogRepository;

    public UserPointLogResponse findMonthlyUserPointLogs(Long userId, Long storeId, LocalDate yearMonth) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user does not exist with id" + userId));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store does not exist with id" + storeId));

        UserPoint userPoint = userPointRepository.findByUserAndStore(user, store)
                .orElseThrow(() -> new NotFoundException("user does not have any point at store"));

        List<UserPointLog> userPointLogs = userPointLogRepository.findMonthlyUserPointLogs(userPoint, yearMonth);

        return new UserPointLogResponse(store, userPointLogs);
    }
}
