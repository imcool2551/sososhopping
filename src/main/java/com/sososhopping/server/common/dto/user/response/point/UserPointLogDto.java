package com.sososhopping.server.common.dto.user.response.point;

import com.sososhopping.server.entity.member.UserPointLog;
import com.sososhopping.server.entity.store.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserPointLogDto {

    private String storeName;
    private String phone;
    private Double lat;
    private Double lng;
    private List<Logs> logs;

    public UserPointLogDto(Store store, List<UserPointLog> userPointLogs) {
        storeName = store.getName();
        phone = store.getPhone();
        lat = store.getLat().doubleValue();
        lng = store.getLng().doubleValue();
        logs = userPointLogs.stream()
                .map(userPointLog -> new Logs(
                        userPointLog.getPointAmount(),
                        userPointLog.getResultAmount(),
                        userPointLog.getCreatedAt()
                                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                )
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    static class Logs {
        private Integer pointAmount;
        private Integer resultAmount;
        private String createdAt;
    }
}
