package com.sososhopping.repository.order;

import com.sososhopping.entity.user.Cart;
import com.sososhopping.entity.user.User;

import java.util.List;

public interface UserCartRepository {

    List<Cart> getCartByUser(User user);
}