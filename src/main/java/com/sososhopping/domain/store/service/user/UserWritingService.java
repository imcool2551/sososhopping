package com.sososhopping.domain.store.service.user;

import com.sososhopping.domain.store.dto.user.response.WritingResponse;
import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.store.repository.WritingRepository;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.Writing;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWritingService {

    private final StoreRepository storeRepository;
    private final WritingRepository writingRepository;

    public WritingResponse findStoreWriting(Long storeId, Long writingId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("no store with id " + storeId));

        Writing writing = writingRepository.findById(writingId)
                .orElseThrow(() -> new NotFoundException("no writing with id " + writingId));

        if (!writing.belongsTo(store)) {
            throw new BadRequestException("writing does not belong to store");
        }

        return new WritingResponse(writing);
    }

    public Slice<WritingResponse> findStoreWritings(Long storeId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("no store with id " + storeId));

        return writingRepository
                .findByStoreOrderByCreatedAtDesc(store, pageable)
                .map(WritingResponse::new);
    }
}
