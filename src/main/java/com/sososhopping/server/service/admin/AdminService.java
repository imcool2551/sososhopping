package com.sososhopping.server.service.admin;

import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.StoreStatus;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StoreRepository storeRepository;

    @Transactional
    public void updateStoreStatus(Long storeId, String action) {

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        if (action.equals("approve")) {
            store.updateStoreStatus(StoreStatus.ACTIVE);
        } else if (action.equals("reject")) {
            store.updateStoreStatus(StoreStatus.REJECT);
        } else {
            throw new Api400Exception("Unknown request");
        }
    }
}
