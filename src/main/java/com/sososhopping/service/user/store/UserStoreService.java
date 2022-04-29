package com.sososhopping.service.user.store;

import com.sososhopping.common.dto.OffsetBasedPageRequest;
import com.sososhopping.common.dto.user.request.store.GetStoreBySearchDto;
import com.sososhopping.common.dto.user.request.store.StoreSearchType;
import com.sososhopping.common.dto.user.response.store.StoreListDto;
import com.sososhopping.domain.store.repository.InterestStoreRepository;
import com.sososhopping.domain.store.repository.JdbcStoreRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.InterestStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Service
@RequiredArgsConstructor
public class UserStoreService {

    private final InterestStoreRepository interestStoreRepository;
    private final StoreRepository storeRepository;
    private final JdbcStoreRepository jdbcStoreRepository;

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
