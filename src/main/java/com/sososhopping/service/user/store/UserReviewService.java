package com.sososhopping.service.user.store;

import com.sososhopping.common.dto.user.request.store.ReviewCreateDto;
import com.sososhopping.common.dto.user.response.store.StoreReviewDto;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.common.error.Api409Exception;
import com.sososhopping.entity.member.Review;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.store.Store;
import com.sososhopping.auth.repository.UserRepository;
import com.sososhopping.repository.store.ReviewRepository;
import com.sososhopping.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserReviewService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final EntityManager em;

    @Transactional
    public List<StoreReviewDto> getStoreReviews(Long storeId) {
        storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        return reviewRepository.findReviewsByStoreIdOrderByCreatedAtDesc(storeId)
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
                .user(user)
                .store(store)
                .build();

        reviewRepository.findByUserAndStore(user, store)
                .ifPresentOrElse(
                        (r) -> {
                            throw new Api409Exception("이미 작성한 리뷱 존재합니다");
                        },
                        () -> reviewRepository.save(review)
                );
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
