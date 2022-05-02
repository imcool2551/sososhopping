package com.sososhopping.service.owner;

import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.service.S3Service;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.store.repository.WritingRepository;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.Writing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreWritingService {

    private final StoreRepository storeRepository;
    private final WritingRepository writingRepository;
    private final S3Service s3Service;
    private final EntityManager em;

    @Transactional
    public List<Writing> readWritingList(Long storeId) {
        Store store = storeRepository.findStoreWithWritingById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        return store.getWritings();
    }

    @Transactional
    public Writing readWriting(Long storeId, Long writingId) {
        return writingRepository.findById(writingId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 글입니다"));
    }


    @Transactional
    public void deleteWriting(Long storeId, Long writingId) {
        writingRepository.deleteById(writingId);
    }

}
