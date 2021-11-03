package com.sososhopping.server.entity.orders;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemId implements Serializable {
    private Long order;
    private Long item;
}
