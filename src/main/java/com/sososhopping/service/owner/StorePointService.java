package com.sososhopping.service.owner;

import com.sososhopping.common.dto.owner.request.StorePointPolicyRequestDto;
import com.sososhopping.common.dto.owner.response.StoreUserPointResponseDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.entity.user.UserPoint;
import com.sososhopping.entity.owner.Store;
import com.sososhopping.entity.user.User;
import com.sososhopping.repository.member.UserPointLogRepository;
import com.sososhopping.repository.member.UserPointRepository;
import com.sososhopping.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorePointService {

    private final StoreRepository storeRepository;
    private final UserAuthRepository userRepository;
    private final UserPointRepository userPointRepository;
    private final UserPointLogRepository userPointLogRepository;

    @Transactional
    public Store readPointPolicy(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        return store;
    }

    @Transactional
    public void updatePointPolicy(Long storeId, StorePointPolicyRequestDto dto) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        store.updatePointPolicy(dto);
    }

//    @Transactional
//    public void updateUserPointDirectly(Long storeId, UserPointUpdateRequestDto dto) {
//        Store store = storeRepository.findById(storeId).orElseThrow(() ->
//                new Api400Exception("존재하지 않는 점포입니다"));
//
//        User user = userRepository.findByPhone(dto.getUserPhone()).orElseThrow(() ->
//                new Api400Exception("존재하지 않는 고객입니다"));
//
//        Optional<UserPoint> result = userPointRepository.findById(new UserPointId(user.getId(), store.getId()));
//
//        if (result.isEmpty()) {
//            if (dto.getIsSave() == true) {//적립인 경우
//                UserPoint userPoint = new UserPoint(user, store, dto.getPointAmount());
//                userPointRepository.save(userPoint);
//
//                UserPointLog userPointLog = new UserPointLog(userPoint, dto.getPointAmount(), dto.getPointAmount());
//                userPointLogRepository.save(userPointLog);
//            } else {//소모인 경우
//                throw new Api403Exception("포인트 내역이 존재하지 않습니다");
//            }
//        } else {
//            UserPoint userPoint = result.get();
//            if (dto.getIsSave() == true) {//적립인 경우
//                userPoint.updatePoint(dto.getPointAmount());
//                UserPointLog userPointLog = new UserPointLog(userPoint, dto.getPointAmount(), userPoint.getPoint());
//                userPointLogRepository.save(userPointLog);
//            } else if (dto.getIsSave() == false) {//소모인 경우
//                if (userPoint.getPoint() < dto.getPointAmount()) {
//                    throw new Api403Exception("해당 고객의 보유 포인트가 " + userPoint.getPoint() + "포인트뿐입니다");
//                } else {
//                    userPoint.updatePoint(dto.getPointAmount() * -1);
//                    UserPointLog userPointLog = new UserPointLog(userPoint, dto.getPointAmount() * -1, userPoint.getPoint());
//                    userPointLogRepository.save(userPointLog);
//                }
//            } else {
//                throw new Api403Exception("포인트 처리 입력이 명확하지 않습니다");
//            }
//        }
//    }

    @Transactional
    public StoreUserPointResponseDto readUserPoint(Long storeId, String userPhone) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        User user = userRepository.findByPhone(userPhone).orElseThrow(() ->
                new Api400Exception("존재하지 않는 고객입니다"));

        Optional<UserPoint> result = userPointRepository.findByUserAndStore(user, store);

        if (result.isEmpty()) {
            return new StoreUserPointResponseDto(user.getNickname(), 0);
        } else {
            return new StoreUserPointResponseDto(user.getNickname(), result.get().getPoint());
        }
    }


}
