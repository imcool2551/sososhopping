package com.sososhopping.domain.point.dto.response;

import com.sososhopping.entity.point.UserPointLog;
import com.sososhopping.entity.store.Store;
import lombok.Data;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MyPointLogResponse {

    private static final DateTimeFormatter DATETIME_PATTERN = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String storeName;
    private String phone;
    private BigDecimal lat;
    private BigDecimal lng;
    private List<LogResponse> logs;

    public MyPointLogResponse(Store store, List<UserPointLog> userPointLogs) {
        storeName = store.getName();
        phone = store.getPhone();
        lat = store.getLat();
        lng = store.getLng();
        logs = userPointLogs.stream()
                .map(userPointLog -> new LogResponse(
                        userPointLog.getUsed(),
                        userPointLog.getResult(),
                        userPointLog.getCreatedAt()
                                .format(DATETIME_PATTERN)))
                .collect(Collectors.toList());
    }

    @Data
    static class LogResponse {
        private int used;
        private int result;
        private String createdAt;

        public LogResponse(Integer used, Integer result, String createdAt) {
            this.used = used;
            this.result = result;
            this.createdAt = createdAt;
        }
    }
}
