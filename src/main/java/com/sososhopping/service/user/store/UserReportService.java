package com.sososhopping.service.user.store;

import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.admin.StoreReport;
import com.sososhopping.entity.store.Store;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.repository.store.StoreReportRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserReportService {

    private final UserAuthRepository userRepository;
    private final StoreRepository storeRepository;
    private final StoreReportRepository storeReportRepository;

    @Transactional
    public void createReport(Long userId, Long storeId, String content) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid token"));

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        StoreReport storeReport = StoreReport.builder()
                .user(user)
                .store(store)
                .content(content)
                .handled(false)
                .build();

        storeReportRepository.save(storeReport);
    }
}
