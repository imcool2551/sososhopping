package com.sososhopping.server.service.user.store;

import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.Writing;
import com.sososhopping.server.repository.store.StoreRepository;
import com.sososhopping.server.repository.store.WritingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserWritingService {

    private final WritingRepository writingRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Writing getWriting(Long storeId, Long writingId) {

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        Writing writing = writingRepository
                .findById(writingId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 글입니다"));

        if (!writing.belongsTo(store)) {
            throw new Api400Exception("잘못된 요청입니다");
        }

        return writing;
    }
}
