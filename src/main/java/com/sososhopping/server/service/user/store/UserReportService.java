package com.sososhopping.server.service.user.store;

import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.report.StoreReport;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.store.StoreReportRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserReportService {

    private final UserRepository userRepository;
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
