package com.sososhopping.entity.orders;

import com.sososhopping.common.exception.BadRequestException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderStatusTest {

    @ParameterizedTest
    @EnumSource(names = {"APPROVE", "REJECT", "READY", "CANCEL", "DONE"})
    void toCancel(OrderStatus status) {
        assertThatThrownBy(() -> status.toCancel()).isInstanceOf(BadRequestException.class);
    }

    @ParameterizedTest
    @EnumSource(names = {"DONE", "CANCEL"})
    void toReject(OrderStatus status) {
        assertThatThrownBy(() -> status.toReject()).isInstanceOf(BadRequestException.class);
    }

    @ParameterizedTest
    @EnumSource(names = {"PENDING", "APPROVE", "REJECT", "CANCEL", "DONE"})
    void toDone(OrderStatus status) {
        assertThatThrownBy(() -> status.toDone()).isInstanceOf(BadRequestException.class);
    }

    @ParameterizedTest
    @EnumSource(names = {"APPROVE", "REJECT", "READY", "CANCEL", "DONE"})
    void toApprove(OrderStatus status) {
        assertThatThrownBy(() -> status.toApprove()).isInstanceOf(BadRequestException.class);
    }

    @ParameterizedTest
    @EnumSource(names = {"PENDING", "REJECT", "READY", "CANCEL", "DONE"})
    void toReady(OrderStatus status) {
        assertThatThrownBy(() -> status.toReady()).isInstanceOf(BadRequestException.class);
    }
}
