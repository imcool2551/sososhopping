package com.sososhopping.domain.store.service.user;

import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.admin.StoreReport;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import com.sososhopping.domain.store.repository.StoreReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserReportService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final StoreReportRepository storeReportRepository;

    public void createReport(Long userId, Long storeId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store with id " + storeId + " does not exist"));

        StoreReport storeReport = StoreReport.builder()
                .user(user)
                .store(store)
                .content(content)
                .handled(false)
                .build();

        storeReportRepository.save(storeReport);
    }
}
