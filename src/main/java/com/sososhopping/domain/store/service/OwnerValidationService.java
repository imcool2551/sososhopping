package com.sososhopping.domain.store.service;

import com.sososhopping.common.exception.ForbiddenException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.owner.repository.OwnerRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.owner.Owner;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerValidationService {

    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;

    public Store validateStoreOwner(Long ownerId, Long storeId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("Store not found with id " + storeId));

        if (!store.belongsTo(owner)) {
            throw new ForbiddenException("Store does not belong to owner");
        }

        return store;
    }
}
