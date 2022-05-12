package com.sososhopping.domain.report.service;

import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.admin.UserReport;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import com.sososhopping.domain.report.repository.UserReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerReportService {

    private final OwnerValidationService ownerValidationService;
    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;

    public Long createUserReport(Long ownerId, Long storeId, Long userId, String content) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);

        UserReport userReport = new UserReport(store, user, content);
        userReportRepository.save(userReport);
        return userReport.getId();
    }
}
