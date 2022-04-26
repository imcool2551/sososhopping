package com.sososhopping.domain.store.service;

import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.common.service.S3Service;
import com.sososhopping.domain.owner.repository.OwnerRepository;
import com.sososhopping.domain.store.dto.request.CreateStoreDto;
import com.sososhopping.domain.store.exception.DuplicateBusinessNumberException;
import com.sososhopping.domain.store.exception.MissingFileException;
import com.sososhopping.domain.store.repository.StoreBusinessDayRepository;
import com.sososhopping.domain.store.repository.StoreMetaDataRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.owner.Owner;
import com.sososhopping.entity.owner.Store;
import com.sososhopping.entity.owner.StoreBusinessDay;
import com.sososhopping.entity.owner.StoreMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;
    private final StoreMetaDataRepository storeMetaDataRepository;
    private final StoreBusinessDayRepository storeBusinessDayRepository;
    private final S3Service s3Service;

    public String upload(Long ownerId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new MissingFileException("missing file");
        }

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);

        String dirPath = owner.getId().toString();
        return s3Service.upload(dirPath, file);
    }

    public Long createStore(Long ownerId, CreateStoreDto dto) {
        String businessNumber = dto.getBusinessNumber();

        if (storeMetaDataRepository.existsByBusinessNumber(businessNumber)) {
            throw new DuplicateBusinessNumberException("businessNumber already in use: " + businessNumber);
        }

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);
        Store store = dto.toStore(owner);
        StoreMetadata storeMetadata = dto.toStoreMetadata(store);
        List<StoreBusinessDay> storeBusinessDays = dto.toStoreBusinessDays(store);
        storeRepository.save(store);
        storeMetaDataRepository.save(storeMetadata);
        for (StoreBusinessDay storeBusinessDay : storeBusinessDays) {
            storeBusinessDayRepository.save(storeBusinessDay);
        }

        return store.getId();
    }
}
