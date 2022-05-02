package com.sososhopping.service.user.order;

import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.domain.orders.repository.CartRepository;
import com.sososhopping.domain.store.repository.ItemRepository;
import com.sososhopping.entity.store.Item;
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

    @Transactional
    public void deleteCartItem(Long userId, Long itemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new Api404Exception("존재하지 않는 물품입니다"));

        cartRepository.findByUserAndItem(user, item)
                .ifPresentOrElse(
                        cart -> em.remove(cart),
                        () -> {
                            throw new Api404Exception("장바구니에 없는 물품입니다");
                        }
                );
    }


}
