package com.sososhopping.server.service.owner;

import com.sososhopping.server.common.dto.owner.request.StorePointPolicyRequestDto;
import com.sososhopping.server.common.dto.owner.request.UserPointUpdateRequestDto;
import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.member.UserPoint;
import com.sososhopping.server.entity.member.UserPointId;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.member.UserPointRepository;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorePointService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;

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

    @Transactional
    public void updateUserPointDirectly(Long storeId, UserPointUpdateRequestDto dto) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        User user = userRepository.findByPhone(dto.getPhone()).orElseThrow(() ->
                new Api400Exception("존재하지 않는 고객입니다"));

        Optional<UserPoint> result = userPointRepository.findById(new UserPointId(user, store));

        if (result == null) {
            if (dto.getIsSave() == true) {
                UserPoint userPoint = new UserPoint(user, store, dto.getPointAmount());
                userPointRepository.save(userPoint);
            } else {
                throw new Api400Exception("포인트 내역이 존재하지 않습니다");
            }
        } else {
            UserPoint userPoint = result.get();
            if (dto.getIsSave() == true) {
                userPoint.updatePoint(dto.getPointAmount());
            } else if (dto.getIsSave() == false) {
                if (userPoint.getPoint() < dto.getPointAmount()) {
                    throw new Api400Exception("해당 고객의 보유 포인트가 " + userPoint.getPoint() + "포인트뿐입니다");
                } else {
                    userPoint.updatePoint(dto.getPointAmount() * -1);
                }
            } else {
                throw new Api400Exception("포인트 처리 입력이 명확하지 않습니다");
            }
        }
    }


}
