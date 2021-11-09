package com.sososhopping.server.entity.member;

import com.sososhopping.server.entity.store.Store;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserPointId implements Serializable {
    private User user;
    private Store store;
}
