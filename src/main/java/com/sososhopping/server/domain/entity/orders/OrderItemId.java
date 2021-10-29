package com.sososhopping.server.domain.entity.orders;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemId implements Serializable {
    private String order;
    private String item;
}
