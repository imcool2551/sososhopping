package com.sososhopping.domain.orders.service.user;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.orders.dto.user.request.AddCartItemDto;
import com.sososhopping.domain.store.repository.ItemRepository;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.user.Cart;
import com.sososhopping.entity.user.User;
import com.sososhopping.repository.order.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
