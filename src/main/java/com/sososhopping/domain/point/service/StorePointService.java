package com.sososhopping.domain.point.service;

import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.domain.point.dto.request.UpdateSaveRateDto;
import com.sososhopping.domain.point.dto.request.UpdateUserPointDto;
import com.sososhopping.domain.point.dto.response.UserPointResponse;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import com.sososhopping.domain.point.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class StorePointService {

    private final OwnerValidationService ownerValidationService;
    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;

    public BigDecimal findSaveRate(Long ownerId, Long storeId) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        return store.getSaveRate();
    }

    public void updateSaveRate(Long ownerId, Long storeId, UpdateSaveRateDto dto) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        boolean pointPolicyStatus = dto.getPointPolicyStatus();
        BigDecimal saveRate = pointPolicyStatus
                ? dto.getSaveRate()
                : BigDecimal.valueOf(0);
        store.updateSaveRate(pointPolicyStatus, saveRate);
    }

    public UserPointResponse findUserPoint(Long ownerId, Long storeId, String userPhone) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        User user = userRepository.findByUserInfoPhone(userPhone)
                .orElseThrow(() -> new NotFoundException("user does not exist with phone: " + userPhone));

        return userPointRepository.findByUserAndStore(user, store)
                .map(userPoint -> new UserPointResponse(user.getName(), userPoint.getPoint()))
                .orElse(new UserPointResponse(user.getName(), 0));
    }

    public void updateUserPoint(Long ownerId, Long storeId, UpdateUserPointDto dto) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        String userPhone = dto.getUserPhone();
        User user = userRepository.findByUserInfoPhone(userPhone)
                .orElseThrow(() -> new NotFoundException("user does not exist with phone: " + userPhone));

        UserPoint userPoint = userPointRepository.findByUserAndStore(user, store)
                .orElseGet(() -> {
                    UserPoint newUserPoint = new UserPoint(user, store, 0);
                    userPointRepository.save(newUserPoint);
                    return newUserPoint;
                });

        userPoint.updatePoint(dto.getAmount());
    }
}
