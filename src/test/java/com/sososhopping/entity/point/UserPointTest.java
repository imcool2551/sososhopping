package com.sososhopping.entity.point;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserPointTest {

    @Test
    void updatePoint_WhenNotEnoughPoint_ThrowsBadRequestException() {
        User user = User.builder().build();
        Store store = Store.builder().build();
        UserPoint userPoint = new UserPoint(user, store, 0);

        assertThatThrownBy(() -> userPoint.updatePoint(-1000))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void updatePoint() {
        User user = User.builder().build();
        Store store = Store.builder().build();
        UserPoint userPoint = new UserPoint(user, store, 1000);

        userPoint.updatePoint(1000);

        assertThat(userPoint.getPoint()).isEqualTo(2000);
        assertThat(userPoint.getUserPointLogs()).hasSize(1);
    }
}
