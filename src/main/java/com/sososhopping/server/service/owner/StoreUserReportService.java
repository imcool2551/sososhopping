package com.sososhopping.server.service.owner;

import com.sososhopping.server.common.dto.owner.request.UserReportRequestDto;
import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.report.UserReport;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.member.UserRepository;
import com.sososhopping.server.repository.report.UserReportRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreUserReportService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;

    //유저 신고
    @Transactional
    public void createUserReport(Long storeId, UserReportRequestDto dto) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        User user = userRepository.findById(dto.getUserId()).orElseThrow(() ->
                new Api400Exception("존재하지 않는 고객입니다"));

        UserReport userReport = new UserReport(store, user, dto.getContent());

        userReportRepository.save(userReport);
    }
}
