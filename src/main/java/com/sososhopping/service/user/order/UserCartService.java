package com.sososhopping.service.user.order;

import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.orders.repository.CartRepository;
import com.sososhopping.domain.store.repository.ItemRepository;
import com.sososhopping.entity.user.Cart;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

//@Service
@RequiredArgsConstructor
public class UserCartService {

    private final UserAuthRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final EntityManager em;

    @Transactional
    public List<Cart> getMyCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        return cartRepository.findByUser(user);
    }


}
