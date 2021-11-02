package com.sososhopping.server.domain.entity.member;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class InterestStoreId implements Serializable {
    private Long user;
    private Long store;
}
