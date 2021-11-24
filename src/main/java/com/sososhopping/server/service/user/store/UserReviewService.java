package com.sososhopping.server.service.user.store;

import com.sososhopping.server.common.dto.user.request.store.ReviewCreateDto;
import com.sososhopping.server.common.dto.user.response.store.StoreReviewDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.member.Review;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.store.ReviewRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserReviewService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public List<StoreReviewDto> getStoreReviews(Long storeId) {
        storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        return reviewRepository.findReviewsByStoreId(storeId)
                .stream()
                .map(StoreReviewDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createReview(Long userId, Long storeId, ReviewCreateDto dto) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid token"));

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        Review review = Review.builder()
                .content(dto.getContent())
                .score(dto.getScore())
                .imgUrl(dto.getImgUrl())
                .build();

        review.setUser(user);
        review.setStore(store);
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long userId, Long storeId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid token"));

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        Review review = reviewRepository.findByUserAndStore(user, store)
                .orElseThrow(() -> new Api404Exception("리뷰가 존재하지 않습니다"));

        reviewRepository.delete(review);
    }

    @Transactional
    public void updateReview(Long userId, Long storeId, ReviewCreateDto dto) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid token"));

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        Review review = reviewRepository.findByUserAndStore(user, store)
                .orElseThrow(() -> new Api404Exception("리뷰가 존재하지 않습니다"));

        review.updateReview(dto.getContent(), dto.getImgUrl(), dto.getScore());
    }

    @Transactional
    public boolean existingReviewByUserAndStore(Long userId, Long storeId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid token"));

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        return reviewRepository.existsByUserAndStore(user, store);
    }
}
