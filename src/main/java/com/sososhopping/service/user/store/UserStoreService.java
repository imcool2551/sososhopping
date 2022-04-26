package com.sososhopping.service.user.store;

import com.sososhopping.common.dto.OffsetBasedPageRequest;
import com.sososhopping.common.dto.user.request.store.GetLocalCurrencyStoreDto;
import com.sososhopping.common.dto.user.request.store.GetStoreByCategoryDto;
import com.sososhopping.common.dto.user.request.store.GetStoreBySearchDto;
import com.sososhopping.common.dto.user.request.store.StoreSearchType;
import com.sososhopping.common.dto.user.response.store.StoreInfoDto;
import com.sososhopping.common.dto.user.response.store.StoreListDto;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.entity.user.InterestStore;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.user.UserPoint;
import com.sososhopping.entity.store.Store;
import com.sososhopping.repository.member.UserPointRepository;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.repository.store.InterestStoreRepository;
import com.sososhopping.repository.store.JdbcStoreRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserStoreService {

    private final InterestStoreRepository interestStoreRepository;
    private final UserAuthRepository userRepository;
    private final StoreRepository storeRepository;
    private final UserPointRepository userPointRepository;
    private final JdbcStoreRepository jdbcStoreRepository;


    @Transactional
    public void toggleStoreLike(Long userId, Long storeId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid token"));

        Store store = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        interestStoreRepository
                .findByUserAndStore(user, store)
                .ifPresentOrElse(
                        interestStoreRepository::delete,
                        () -> interestStoreRepository.save(new InterestStore(user, store))
                );
    }

    @Transactional
    public StoreInfoDto getStoreInfo(Long userId, Long storeId) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        if (userId == null) {
            return new StoreInfoDto(findStore, false, 0);
        }

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 유저입니다"));

        UserPoint userPoint = userPointRepository.findByUserAndStore(user, findStore)
                .orElse(null);

        Integer myPoint = null;
        if (userPoint != null) {
            myPoint = userPoint.getPoint();
        }

        boolean isInterestStore = interestStoreRepository.existsByStoreAndUser(findStore, user);
        return new StoreInfoDto(findStore, isInterestStore, myPoint);
    }

    @Transactional
    public List<StoreListDto> getStoresByCategory(
            Long userId,
            GetStoreByCategoryDto dto
    ) {

        Map<Long, Double> nearStoreIdsByCategory = jdbcStoreRepository
                .getNearStoreIdsByCategory(dto.getLat(), dto.getLng(), dto.getRadius(), dto.getType(), dto.getOffset());

        List<Long> storeIds = new ArrayList<>(nearStoreIdsByCategory.keySet());

        List<Store> stores = storeRepository.findByIdIn(storeIds);

        if (userId == null) {
            return stores.stream()
                    .map(store -> new StoreListDto(store, Collections.emptyList(), nearStoreIdsByCategory))
                    .sorted()
                    .collect(Collectors.toList());
        }

        List<InterestStore> interestStores = interestStoreRepository.findAllByUserId(userId);
        return stores.stream()
                .map(store -> new StoreListDto(store, interestStores, nearStoreIdsByCategory))
                .sorted()
                .collect(Collectors.toList());
    }

    @Transactional
    public Slice<StoreListDto> getStoresByCategoryPageable(
            Long userId,
            GetStoreByCategoryDto dto
    ) {

        Map<Long, Double> nearStoreIdsByCategory = jdbcStoreRepository
                .getNearStoreIdsByCategory(dto.getLat(), dto.getLng(), dto.getRadius(), dto.getType(), dto.getOffset());

        List<Long> storeIds = new ArrayList<>(nearStoreIdsByCategory.keySet());

        List<Store> stores = storeRepository.findByIdIn(storeIds);

        List<StoreListDto> content;

        if (userId == null) {
            content = stores.stream()
                    .map(store -> new StoreListDto(store, Collections.emptyList(), nearStoreIdsByCategory))
                    .sorted()
                    .collect(Collectors.toList());
        } else {
            List<InterestStore> interestStores = interestStoreRepository.findAllByUserId(userId);
            content = stores.stream()
                    .map(store -> new StoreListDto(store, interestStores, nearStoreIdsByCategory))
                    .sorted()
                    .collect(Collectors.toList());
        }

        Pageable pageable = new OffsetBasedPageRequest(dto.getOffset(), 10);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Transactional
    public Slice<StoreListDto> getLocalCurrencyStores(Long userId, GetLocalCurrencyStoreDto dto) {
        Map<Long, Double> nearLocalCurrencyStoreIds =
                jdbcStoreRepository.getLocalCurrencyStores(dto.getLat(), dto.getLng(), dto.getRadius(), dto.getOffset());

        List<Long> storeIds = new ArrayList<>(nearLocalCurrencyStoreIds.keySet());

        List<Store> stores = storeRepository.findByIdIn(storeIds);

        List<StoreListDto> content;

        if (userId == null) {
            content = stores.stream()
                    .map(store -> new StoreListDto(store, Collections.emptyList(), nearLocalCurrencyStoreIds))
                    .sorted()
                    .collect(Collectors.toList());
        } else {
            List<InterestStore> interestStores = interestStoreRepository.findAllByUserId(userId);
            content = stores.stream()
                    .map(store -> new StoreListDto(store, interestStores, nearLocalCurrencyStoreIds))
                    .sorted()
                    .collect(Collectors.toList());
        }

        Pageable pageable = new OffsetBasedPageRequest(dto.getOffset(), 10);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Transactional
    public List<StoreListDto> getStoreBySearch(Long userId, GetStoreBySearchDto dto) {
        Map<Long, Double> nearStoreIdsBySearch;
        StoreSearchType searchType = dto.getType();
        if (searchType.equals(StoreSearchType.STORE)) {
            nearStoreIdsBySearch = jdbcStoreRepository
                    .getNearStoreIdsByStoreName(dto.getLat(), dto.getLng(), dto.getRadius(), dto.getQ(), dto.getOffset());
        } else {
            nearStoreIdsBySearch = jdbcStoreRepository
                    .getNearStoreIdsByItemName(dto.getLat(), dto.getLng(), dto.getRadius(), dto.getQ(), dto.getOffset());
        }

        List<Long> storeIds = new ArrayList<>(nearStoreIdsBySearch.keySet());

        List<Store> stores = storeRepository.findByIdIn(storeIds);

        if (userId == null) {
            return stores.stream()
                    .map(store -> new StoreListDto(store, Collections.emptyList(), nearStoreIdsBySearch))
                    .sorted()
                    .collect(Collectors.toList());
        }

        List<InterestStore> interestStores = interestStoreRepository.findAllByUserId(userId);
        return stores.stream()
                .map(store -> new StoreListDto(store, interestStores, nearStoreIdsBySearch))
                .sorted()
                .collect(Collectors.toList());
    }

    @Transactional
    public Slice<StoreListDto> getStoreBySearchPageable(Long userId, GetStoreBySearchDto dto) {
        Map<Long, Double> nearStoreIdsBySearch;
        StoreSearchType searchType = dto.getType();
        if (searchType.equals(StoreSearchType.STORE)) {
            nearStoreIdsBySearch = jdbcStoreRepository
                    .getNearStoreIdsByStoreName(dto.getLat(), dto.getLng(), dto.getRadius(), dto.getQ(), dto.getOffset());
        } else {
            nearStoreIdsBySearch = jdbcStoreRepository
                    .getNearStoreIdsByItemName(dto.getLat(), dto.getLng(), dto.getRadius(), dto.getQ(), dto.getOffset());
        }

        List<Long> storeIds = new ArrayList<>(nearStoreIdsBySearch.keySet());

        List<Store> stores = storeRepository.findByIdIn(storeIds);
        List<StoreListDto> content;

        if (userId == null) {
            content = stores.stream()
                    .map(store -> new StoreListDto(store, Collections.emptyList(), nearStoreIdsBySearch))
                    .sorted()
                    .collect(Collectors.toList());
        } else {
            List<InterestStore> interestStores = interestStoreRepository.findAllByUserId(userId);
            content = stores.stream()
                    .map(store -> new StoreListDto(store, interestStores, nearStoreIdsBySearch))
                    .sorted()
                    .collect(Collectors.toList());
        }

        Pageable pageable = new OffsetBasedPageRequest(dto.getOffset(), 10);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(content.size() - 1);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
