package com.sososhopping.controller.user.store;

import com.sososhopping.common.dto.user.response.point.UserPointListDto;
import com.sososhopping.common.dto.user.response.point.UserPointLogDto;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.user.UserPoint;
import com.sososhopping.entity.user.UserPointLog;
import com.sososhopping.entity.owner.Store;
import com.sososhopping.repository.member.UserPointLogRepository;
import com.sososhopping.repository.member.UserPointRepository;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.repository.store.InterestStoreRepository;
import com.sososhopping.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.*;

@RestController
@RequiredArgsConstructor
public class UserPointController {

    private final UserAuthRepository userRepository;
    private final StoreRepository storeRepository;
    private final InterestStoreRepository interestStoreRepository;
    private final UserPointRepository userPointRepository;
    private final UserPointLogRepository userPointLogRepository;

    @GetMapping("/api/v1/users/my/points")
    public UserPointListDto getMyPoints(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        List<UserPoint> userPoints = userPointRepository.findByUser(user);

        List<Store> interestStores = interestStoreRepository.findAllByUserId(userId)
                .stream()
                .map(interestStore -> interestStore.getStore())
                .collect(Collectors.toList());

        return new UserPointListDto(userPoints, interestStores);
    }

    @GetMapping("/api/v1/users/my/points/{storeId}")
    public UserPointLogDto getMyPointLogs(
            Authentication authentication,
            @PathVariable Long storeId,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate at
    ) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new Api404Exception("점포가 존재하지 않습니다"));

        UserPoint userPoint = userPointRepository.findByUserAndStore(user, store)
                .orElseThrow(() -> new Api404Exception("포인트가 존재하지 않습니다"));

        List<UserPointLog> userPointLogs = userPointLogRepository
                .findMonthlyUserPointLogs(userPoint, at.atStartOfDay());

        return new UserPointLogDto(store, userPointLogs);
    }
}
