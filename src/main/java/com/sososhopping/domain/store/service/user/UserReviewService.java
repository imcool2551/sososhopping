package com.sososhopping.domain.store.service.user;

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
import com.sososhopping.domain.store.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserReviewService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

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

    public Slice<StoreReviewResponse> findStoreReviews(Long storeId, Pageable pageable) {
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

    public List<StoreReviewResponse> findMyReviews(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        return reviewRepository.findByUser(user)
                .stream()
                .map(StoreReviewResponse::new)
                .collect(Collectors.toList());
    }

    public boolean checkReview(Long userId, Long storeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store with id " + storeId + " does not exist"));

        return reviewRepository.existsByUserAndStore(user, store);
    }

    public void deleteReview(Long userId, Long storeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store with id " + storeId + " does not exist"));


        reviewRepository.findByUserAndStore(user, store)
                .ifPresentOrElse(
                        reviewRepository::delete,
                        () -> {
                            throw new BadRequestException("review does not exist");
                        });
    }

}
