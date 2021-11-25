package com.sososhopping.server.repository.order;

import com.sososhopping.server.entity.member.Cart;
import com.sososhopping.server.entity.member.User;

import java.util.List;

public interface UserCartRepository {

    List<Cart> getCartByUser(User user);
}
