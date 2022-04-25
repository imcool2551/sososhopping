package com.sososhopping.service.owner;

import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.entity.user.Review;
import com.sososhopping.entity.owner.Store;
import com.sososhopping.repository.store.ReviewRepository;
import com.sososhopping.repository.store.StoreRepository;
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
