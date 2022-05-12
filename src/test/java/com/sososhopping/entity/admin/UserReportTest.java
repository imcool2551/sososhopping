package com.sososhopping.entity.admin;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.user.UserLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.sososhopping.entity.common.AccountStatus.ACTIVE;
import static com.sososhopping.entity.common.AccountStatus.SUSPEND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserReportTest {

    private static User userA;
    private static User userB;
    private static Store storeA = Store.builder().build();

    @BeforeEach
    void setUp() {
        userA = User.builder().active(ACTIVE).build();
        userB = User.builder().active(ACTIVE).build();
    }

    @Test
    void approve_WhenUserMisMatch_ThrowsBadRequestException() {
        UserReport userReport = new UserReport(storeA, userA, "content");

        assertThatThrownBy(() -> userReport.approve(userB))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void approve() {
        String content = "content";
        UserReport userReport = new UserReport(storeA, userA, content);

        UserLog userLog = userReport.approve(userA);

        assertThat(userA.isActive()).isFalse();
        assertThat(userReport.isHandled()).isTrue();
        assertThat(userLog).isEqualTo(new UserLog(userA, SUSPEND, content));
    }

    @Test
    void reject_WhenUserMisMatch_ThrowsBadRequestException() {
        UserReport userReport = new UserReport(storeA, userA, "content");

        assertThatThrownBy(() -> userReport.reject(userB))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void reject() {
        String content = "content";
        UserReport userReport = new UserReport(storeA, userA, content);

        UserLog userLog = userReport.reject(userA);

        assertThat(userA.isActive()).isTrue();
        assertThat(userReport.isHandled()).isTrue();
        assertThat(userLog).isEqualTo(new UserLog(userA, ACTIVE, content));
    }
}
