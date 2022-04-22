package com.sososhopping.service.user.order;

import com.sososhopping.common.dto.user.request.order.AddCartItemDto;
import com.sososhopping.common.dto.user.request.order.UpdateCartDto;
import com.sososhopping.common.dto.user.request.order.UpdateCartDto.UpdateCartItemDto;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.common.error.Api404Exception;
import com.sososhopping.common.error.Api409Exception;
import com.sososhopping.entity.member.Cart;
import com.sososhopping.entity.member.User;
import com.sososhopping.entity.store.Item;
import com.sososhopping.auth.repository.UserRepository;
import com.sososhopping.repository.order.CartRepository;
import com.sososhopping.repository.store.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCartService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final EntityManager em;

    @Transactional
    public void addCartItem(Long userId, AddCartItemDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new Api404Exception("존재하지 않는 물품입니다"));

        if (cartRepository.existsByUserAndItem(user, item)) {
            throw new Api409Exception("이미 장바구니에 담았습니다");
        }

        Cart cart = Cart.builder()
                .user(user)
                .item(item)
                .quantity(dto.getQuantity())
                .build();

        cartRepository.save(cart);
    }

    @Transactional
    public List<Cart> getMyCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        return cartRepository.getCartByUser(user);
    }

    @Transactional
    public void updateCart(Long userId, UpdateCartDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        List<Cart> userCarts = cartRepository.findByUser(user);
        List<UpdateCartItemDto> requests = dto.getRequests();

        requests.forEach((request) -> {
            Long itemId = request.getItemId();
            Integer quantity = request.getQuantity();

            userCarts
                    .stream()
                    .filter(userCart -> itemId == userCart.getItem().getId())
                    .findAny()
                    .ifPresentOrElse(
                            cart -> {
                                cart.updateQuantity(quantity);
                            },
                            () -> {
                                throw new Api404Exception("장바구니에 없는 물품입니다");
                            }
                    );
        });
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
