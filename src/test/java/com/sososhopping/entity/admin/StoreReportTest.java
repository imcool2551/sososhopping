package com.sososhopping.entity.admin;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.StoreLog;
import com.sososhopping.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.sososhopping.entity.store.StoreStatus.ACTIVE;
import static com.sososhopping.entity.store.StoreStatus.SUSPEND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StoreReportTest {

    private static User userA = User.builder().build();
    private static Store storeA;
    private static Store storeB;

    @BeforeEach
    void setUp() {
        storeA = Store.builder().storeStatus(ACTIVE).build();
        storeB = Store.builder().storeStatus(ACTIVE).build();
    }

    @Test
    void approve_WhenStoreMisMatch_ThrowsBadRequestException() {
        StoreReport storeReport = new StoreReport(userA, storeA, "content");

        assertThatThrownBy(() -> storeReport.approve(storeB))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void approve() {
        String content = "content";
        StoreReport storeReport = new StoreReport(userA, storeA, content);

        StoreLog storeLog = storeReport.approve(storeA);

        assertThat(storeA.getStoreStatus()).isEqualTo(SUSPEND);
        assertThat(storeReport.isHandled()).isTrue();
        assertThat(storeLog).isEqualTo(new StoreLog(storeA, SUSPEND, content));
    }

    @Test
    void reject_WhenStoreMisMatch_ThrowsBadRequestException() {
        StoreReport storeReport = new StoreReport(userA, storeA, "content");

        assertThatThrownBy(() -> storeReport.reject(storeB))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void reject() {
        String content = "content";
        StoreReport storeReport = new StoreReport(userA, storeA, content);

        StoreLog storeLog = storeReport.reject(storeA);

        assertThat(storeA.getStoreStatus()).isEqualTo(ACTIVE);
        assertThat(storeReport.isHandled()).isTrue();
        assertThat(storeLog).isEqualTo(new StoreLog(storeA, ACTIVE, content));
    }
}
