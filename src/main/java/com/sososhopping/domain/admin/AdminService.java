package com.sososhopping.domain.admin;

import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.entity.common.AccountStatus;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.user.UserLog;
import com.sososhopping.entity.admin.StoreReport;
import com.sososhopping.entity.admin.UserReport;
import com.sososhopping.entity.owner.Store;
import com.sososhopping.entity.owner.StoreLog;
import com.sososhopping.entity.owner.StoreStatus;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.repository.report.UserLogRepository;
import com.sososhopping.repository.report.UserReportRepository;
import com.sososhopping.repository.store.StoreLogRepository;
import com.sososhopping.repository.store.StoreReportRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserAuthRepository userRepository;
    private final UserReportRepository userReportRepository;
    private final UserLogRepository userLogRepository;
    private final StoreRepository storeRepository;
    private final StoreReportRepository storeReportRepository;
    private final StoreLogRepository storeLogRepository;

    @Transactional
    public void updateStoreStatus(Long storeId, String action) {

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        if (!isValidAction(action)) {
            throw new Api400Exception("Unknown request");
        }

        if (action.equals("approve")) {
            store.updateStoreStatus(StoreStatus.ACTIVE);
        } else if (action.equals("reject")) {
            store.updateStoreStatus(StoreStatus.REJECT);
        }
    }

    @Transactional
    public void handleStoreReport(Long reportId, Long storeId, String action, String description) {
        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        StoreReport storeReport = storeReportRepository
                .findById(reportId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 신고입니다"));


        if (!isValidAction(action)) {
            throw new Api400Exception("Unknown request");
        }

        if (action.equals("approve")) {
            store.updateStoreStatus(StoreStatus.SUSPEND);
            StoreLog storeLog = StoreLog.builder()
                    .store(store)
                    .storeStatus(StoreStatus.SUSPEND)
                    .description(description)
                    .build();
            storeLogRepository.save(storeLog);
        } else if (action.equals("reject")) {
            StoreLog storeLog = StoreLog.builder()
                    .store(store)
                    .storeStatus(store.getStoreStatus())
                    .description(description)
                    .build();
            storeLogRepository.save(storeLog);
        }

        storeReport.setHandled(true);
    }

    @Transactional
    public void handleUserReport(Long reportId, Long userId, String action, String description) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 유저입니다"));

        UserReport userReport = userReportRepository
                .findById(reportId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 신고입니다"));


        if (!isValidAction(action)) {
            throw new Api400Exception("Unknown request");
        }

        if (action.equals("approve")) {
            user.suspend();
            UserLog userLog = UserLog.builder()
                    .user(user)
                    .active(AccountStatus.SUSPEND)
                    .description(description)
                    .build();
            userLogRepository.save(userLog);
        } else if (action.equals("reject")) {
            UserLog userLog = UserLog.builder()
                    .user(user)
                    .active(user.getActive())
                    .description(description)
                    .build();
            userLogRepository.save(userLog);
        }

        userReport.setHandled(true);
    }

    private boolean isValidAction(String action) {
        return action.equals("approve") || action.equals("reject");
    }

}