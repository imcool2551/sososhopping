package com.sososhopping.domain.store.service.user;

import com.sososhopping.common.dto.OffsetBasedPageRequest;
import com.sososhopping.domain.store.dto.user.request.StoreSearchType;
import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.store.dto.user.request.StoreSearchByCategoryDto;
import com.sososhopping.domain.store.dto.user.request.StoreSearchByKeywordDto;
import com.sososhopping.domain.store.dto.user.response.StoresResponse;
import com.sososhopping.domain.store.repository.InterestStoreRepository;
import com.sososhopping.domain.store.repository.StoreSearchRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.InterestStore;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.sososhopping.domain.store.dto.user.request.StoreSearchType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreSearchService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final InterestStoreRepository interestStoreRepository;
    private final StoreSearchRepository storeSearchRepository;


    public Slice<StoresResponse> findStoresByCategory(Long userId, StoreSearchByCategoryDto dto) {
        Map<Long, Double> idToDistance = findIdToDistance(dto);
        Pageable pageable = new OffsetBasedPageRequest(dto.getOffset(), dto.getLimit());
        return buildStoreResponse(userId, idToDistance, pageable);
    }

    public Slice<StoresResponse> findStoresBySearch(Long userId, StoreSearchByKeywordDto dto) {
        Map<Long, Double> idToDistance = findIdToDistance(dto);
        Pageable pageable = new OffsetBasedPageRequest(dto.getOffset(), dto.getLimit());
        return buildStoreResponse(userId, idToDistance, pageable);
    }

    private Map<Long, Double> findIdToDistance(StoreSearchByCategoryDto dto) {
        return storeSearchRepository.getNearStoreIdsByCategory(dto.getLat(), dto.getLng(), dto.getRadius(),
                                                               dto.getType(), dto.getOffset(), dto.getLimit());
    }

    private Map<Long, Double> findIdToDistance(StoreSearchByKeywordDto dto) {
        StoreSearchType searchType = dto.getType();
        if (searchType == STORE) {
            return storeSearchRepository.getNearStoreIdsByStoreName(dto.getLat(), dto.getLng(), dto.getRadius(),
                    dto.getQ(), dto.getOffset(), dto.getLimit());
        } else if (searchType == ITEM) {
            return storeSearchRepository.getNearStoreIdsByItemName(dto.getLat(), dto.getLng(), dto.getRadius(),
                    dto.getQ(), dto.getOffset(), dto.getLimit());
        }
        throw new BadRequestException("can't process search type: " + searchType.name());
    }

    private SliceImpl<StoresResponse> buildStoreResponse(Long userId, Map<Long, Double> idToDistance, Pageable pageable) {
        List<Store> interestStores = findInterestStore(userId);
        List<Store> stores = storeRepository.findByIdIn(new ArrayList<>(idToDistance.keySet()));
        List<StoresResponse> content = stores.stream()
                .map(store -> new StoresResponse(store, interestStores, idToDistance))
                .sorted(Comparator.comparingDouble(StoresResponse::getDistance))
                .collect(Collectors.toList());
        boolean hasNext = removeLastContentIfHasNext(content, pageable);
        return new SliceImpl<>(content, pageable, hasNext);
    }

    private List<Store> findInterestStore(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        return interestStoreRepository.findByUser(user)
                .stream()
                .map(InterestStore::getStore)
                .collect(Collectors.toList());
    }

    private boolean removeLastContentIfHasNext(List<StoresResponse> content, Pageable pageable) {
        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) {
            content.remove(pageable.getPageSize());
        }
        return hasNext;
    }
}
