package com.sososhopping.server.service.user.store;

import com.sososhopping.server.common.dto.user.request.store.SearchStoreByCategoryDto;
import com.sososhopping.server.common.dto.user.response.store.StoreInfoDto;
import com.sososhopping.server.common.dto.user.response.store.StoreListDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.member.InterestStore;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.StoreType;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.store.InterestStoreRepository;
import com.sososhopping.server.repository.store.JdbcStoreRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserStoreService {

    private final InterestStoreRepository interestStoreRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
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
                        existingStoreLike -> interestStoreRepository.delete(existingStoreLike),
                        () -> interestStoreRepository.save(new InterestStore(user, store))
                );
    }

    @Transactional
    public StoreInfoDto getStoreInfo(Long userId, Long storeId) {

        Store findStore = storeRepository
                .findById(storeId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 점포입니다"));

        if (userId == null) {
            return new StoreInfoDto(findStore, false);
        }

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 유저입니다"));

        boolean isInterestStore = interestStoreRepository.existsByStoreAndUser(findStore, user);
        return new StoreInfoDto(findStore, isInterestStore);
    }

    @Transactional
    public List<StoreListDto> getStoresByCategory(
            Long userId,
            SearchStoreByCategoryDto dto
    ) {

        Map<Long, Double> nearStoreIdsByCategory = jdbcStoreRepository
                .getNearStoreIdsByCategory(dto.getLat(), dto.getLng(), dto.getRadius(), dto.getType());

        List<Long> storeIds = nearStoreIdsByCategory.keySet().stream()
                .collect(Collectors.toList());

        List<Store> stores = storeRepository.findByIdIn(storeIds);

        if (userId == null) {
            return stores.stream()
                    .map(store -> new StoreListDto(store, Collections.emptyList(), nearStoreIdsByCategory))
                    .collect(Collectors.toList());
        }

        List<InterestStore> interestStores = interestStoreRepository.findAllByUserId(userId);
        return stores.stream()
                .map(store -> new StoreListDto(store, interestStores, nearStoreIdsByCategory))
                .collect(Collectors.toList());
    }
}
