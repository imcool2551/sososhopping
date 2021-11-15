package com.sososhopping.server.service.admin;

import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.member.AccountStatus;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.member.UserLog;
import com.sososhopping.server.entity.report.StoreReport;
import com.sososhopping.server.entity.report.UserReport;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.StoreLog;
import com.sososhopping.server.entity.store.StoreStatus;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.report.UserLogRepository;
import com.sososhopping.server.repository.report.UserReportRepository;
import com.sososhopping.server.repository.store.StoreLogRepository;
import com.sososhopping.server.repository.store.StoreReportRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
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
