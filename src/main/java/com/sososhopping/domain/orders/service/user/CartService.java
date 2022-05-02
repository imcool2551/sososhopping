package com.sososhopping.domain.orders.service.user;

import com.sososhopping.domain.orders.dto.user.request.UpdateCartDto;
import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.orders.dto.user.request.AddCartItemDto;
import com.sososhopping.domain.store.repository.ItemRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.user.Cart;
import com.sososhopping.entity.user.User;
import com.sososhopping.domain.orders.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;


    public Long addCartItem(Long userId, AddCartItemDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new NotFoundException("no item with id " + dto.getItemId()));

        if (cartRepository.existsByUserAndItem(user, item)) {
            throw new BadRequestException("item is already in cart");
        }

        Cart cart = new Cart(user, item, dto.getQuantity());
        cartRepository.save(cart);
        return cart.getId();
    }

    public void updateCartItem(Long userId, UpdateCartDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        List<Cart> carts = cartRepository.findByUser(user);
        List<AddCartItemDto> requests = dto.getRequests();

        requests.forEach(request -> {
            Long itemId = request.getItemId();
            Integer quantity = request.getQuantity();

            Cart cart = carts.stream()
                    .filter(c -> c.matchItemId(itemId))
                    .findAny()
                    .orElseThrow(() -> new NotFoundException("cart does not have item with id " + itemId));

            cart.updateQuantity(quantity);
        });
    }

    public void deleteCartItem(Long userId, Long itemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("no item with id " + itemId));

        cartRepository.findByUserAndItem(user, item)
                .ifPresentOrElse(
                        cartRepository::delete,
                        () -> {
                            throw new NotFoundException("cart does not have item with id " + itemId);
                        }
                );
    }
}
