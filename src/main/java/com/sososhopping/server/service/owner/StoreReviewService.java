package com.sososhopping.server.service.owner;

import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.entity.member.Review;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.member.ReviewRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreReviewService {

    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public List<Review> readReviewList(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        return reviewRepository.findByStoreOrderByCreatedAtDesc(store);
    }

}
