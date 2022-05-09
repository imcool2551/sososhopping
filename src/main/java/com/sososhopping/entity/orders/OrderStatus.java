package com.sososhopping.entity.orders;

import com.sososhopping.common.exception.BadRequestException;

public enum OrderStatus {
    PENDING,
    APPROVE,
    REJECT,
    READY,
    CANCEL,
    DONE;

    public OrderStatus toCancel() {
        if (this != PENDING) {
            throw new BadRequestException("order is already in progress");
        }
        return CANCEL;
    }

    public OrderStatus toReject() {
        if (this == DONE || this == CANCEL) {
            throw new BadRequestException("order can't be rejected if it is already done or cancelled");
        }
        return REJECT;
    }

    public OrderStatus toDone() {
        if (this != READY) {
            throw new BadRequestException("order can't be confirmed if it is not ready");
        }
        return DONE;
    }

    public OrderStatus toApprove() {
        if (this != PENDING) {
            throw new BadRequestException("order can't be approved if it is not pending");
        }
        return APPROVE;
    }

    public OrderStatus toReady() {
        if (this != APPROVE) {
            throw new BadRequestException("order can't be ready if it is not approved");
        }
        return READY;
    }
}

