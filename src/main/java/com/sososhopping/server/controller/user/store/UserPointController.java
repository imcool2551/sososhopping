package com.sososhopping.server.controller.user.store;

import com.sososhopping.server.common.dto.user.response.point.UserPointListDto;
import com.sososhopping.server.common.dto.user.response.point.UserPointLogDto;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api404Exception;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.member.UserPoint;
import com.sososhopping.server.entity.member.UserPointLog;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.member.UserPointLogRepository;
import com.sososhopping.server.repository.member.UserPointRepository;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.store.InterestStoreRepository;
import com.sososhopping.server.repository.store.StoreRepository;
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

    private final UserRepository userRepository;
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
