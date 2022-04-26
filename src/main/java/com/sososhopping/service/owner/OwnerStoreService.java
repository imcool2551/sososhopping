package com.sososhopping.service.owner;

import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.domain.auth.repository.OwnerAuthRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.owner.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerStoreService {

    private final OwnerAuthRepository ownerRepository;
    private final StoreRepository storeRepository;

    //점주의 특정 점포 조회
    @Transactional
    public Store readStore(Long storeId) {
        return storeRepository.findStoreInforById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));
    }

    //해당 점포의 영업 여부 조회
    @Transactional
    public Boolean readStoreBusinessStatus(Long storeId) {
        Store store = storeRepository.findStoreInforById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        return store.isOpen();
    }

    //해당 점포의 영업 여부 변경
    @Transactional
    public Boolean updateStoreBusinessStatus(Long storeId) {
        Store store = storeRepository.findStoreInforById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        store.updateBusinessStatus(!store.isOpen());

        return store.isOpen();
    }
}

