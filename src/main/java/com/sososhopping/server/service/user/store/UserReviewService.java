package com.sososhopping.server.service.user.store;

import com.sososhopping.server.common.dto.user.request.store.ReviewCreateDto;
import com.sososhopping.server.common.dto.user.response.store.ReviewDto;
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
    public List<ReviewDto> getStoreReviews(Long storeId) {
        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        List<ReviewDto> reviewDtoList = reviewRepository.findReviewsByStore(storeId)
                .stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());

        return reviewDtoList;
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
}
