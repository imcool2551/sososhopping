package com.sososhopping.service.user.store;

import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.store.dto.user.request.CreateReviewDto;
import com.sososhopping.domain.store.dto.user.response.StoreReviewResponse;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.Review;
import com.sososhopping.entity.user.User;
import com.sososhopping.repository.store.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
    public void createReview(Long userId, Long storeId, CreateReviewDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store with id " + storeId + " does not exist"));

        Review review = dto.toEntity(store, user);

        reviewRepository.findByUserAndStore(user, store)
                .ifPresentOrElse(
                        (r) -> {
                            throw new BadRequestException("review already exists");
                        },
                        () -> reviewRepository.save(review));
    }

    @Transactional
    public Slice<StoreReviewResponse> findReviews(Long storeId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store with id " + storeId + " does not exist"));

        List<StoreReviewResponse> content = reviewRepository
                .findByStoreOrderByCreatedAtDesc(store, pageable)
                .stream()
                .map(StoreReviewResponse::new)
                .collect(Collectors.toList());

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) {
            content.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(content, pageable, hasNext);
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
    public void updateReview(Long userId, Long storeId, CreateReviewDto dto) {
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
