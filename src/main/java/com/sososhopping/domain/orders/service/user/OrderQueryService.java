package com.sososhopping.domain.orders.service.user;

import com.sososhopping.domain.orders.dto.user.response.OrderResponse;
import com.sososhopping.domain.orders.repository.OrderRepository;
import com.sososhopping.domain.orders.dto.user.response.OrderDetailResponse;
import com.sososhopping.common.exception.ForbiddenException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.user.repository.UserRepository;
import com.sososhopping.entity.orders.Order;
import com.sososhopping.entity.orders.OrderItem;
import com.sososhopping.entity.orders.OrderStatus;
import com.sososhopping.entity.owner.Owner;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    public OrderDetailResponse findOrder(Long userId, Long orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        Order order = orderRepository.findOrderDetailsById(orderId)
                .orElseThrow(() -> new NotFoundException("can't find order with id " + orderId));

        if (!order.belongsTo(user)) {
            throw new ForbiddenException("order does not belong to user");
        }

        Store store = order.getStore();
        Owner owner = store.getOwner();
        return new OrderDetailResponse(user, owner, order, store);
    }

    public Slice<OrderResponse> findOrders(Long userId, List<OrderStatus> statuses, Pageable pageable) {

        User user = userRepository.findById(userId)
                .orElseThrow(UnAuthorizedException::new);

        return orderRepository.findOrdersByUserAndOrderStatus(user, statuses, pageable)
                .map(order -> {
                    List<OrderItem> orderItems = order.getOrderItems();
                    Item firstItem = orderItems.get(0)
                            .getItem();
                    return new OrderResponse(order, firstItem, orderItems.size());
                });
    }
}
