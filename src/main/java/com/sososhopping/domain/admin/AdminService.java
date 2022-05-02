package com.sososhopping.domain.admin;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.report.repository.StoreReportRepository;
import com.sososhopping.domain.report.repository.UserReportRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.admin.StoreReport;
import com.sososhopping.entity.admin.UserReport;
import com.sososhopping.entity.common.AccountStatus;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.StoreLog;
import com.sososhopping.entity.store.StoreStatus;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.user.UserLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private static final String APPROVE = "approve";
    private static final String REJECT = "reject";

    private final UserAuthRepository userRepository;
    private final UserReportRepository userReportRepository;
    private final UserLogRepository userLogRepository;
    private final StoreRepository storeRepository;
    private final StoreReportRepository storeReportRepository;
    private final StoreLogRepository storeLogRepository;


    @Transactional
    public void updateStoreStatus(Long storeId, String action) {
        validateAction(action);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("can't find store with id" + storeId));

        if (action.equals(APPROVE)) {
            store.updateStoreStatus(StoreStatus.ACTIVE);
        } else if (action.equals(REJECT)) {
            store.updateStoreStatus(StoreStatus.REJECT);
        }
    }

    @Transactional
    public void handleStoreReport(Long reportId, Long storeId, String action, String description) {
        validateAction(action);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("can't find store with id" + storeId));

        StoreReport storeReport = storeReportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("can't find store report with id" + reportId));

        if (action.equals(APPROVE)) {
            store.updateStoreStatus(StoreStatus.SUSPEND);
            StoreLog storeLog = StoreLog.builder()
                    .store(store)
                    .storeStatus(StoreStatus.SUSPEND)
                    .description(description)
                    .build();
            storeLogRepository.save(storeLog);
        } else if (action.equals(REJECT)) {
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
        validateAction(action);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("can't find user with id " + userId));

        UserReport userReport = userReportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("can't find user report with id" + reportId));


        if (action.equals(APPROVE)) {
            user.suspend();
            UserLog userLog = UserLog.builder()
                    .user(user)
                    .active(AccountStatus.SUSPEND)
                    .description(description)
                    .build();
            userLogRepository.save(userLog);
        } else if (action.equals(REJECT)) {
            UserLog userLog = UserLog.builder()
                    .user(user)
                    .active(user.getActive())
                    .description(description)
                    .build();
            userLogRepository.save(userLog);
        }

        userReport.setHandled(true);
    }

    private void validateAction(String action) {
        if (action.equals(APPROVE) || action.equals(REJECT)) {
            return;
        }
        throw new BadRequestException("unknown action: " + action);
    }

}
