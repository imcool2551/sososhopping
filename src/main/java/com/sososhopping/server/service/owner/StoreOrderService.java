package com.sososhopping.server.service.owner;

import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.common.error.Api401Exception;
import com.sososhopping.server.common.error.Api403Exception;
import com.sososhopping.server.entity.member.Owner;
import com.sososhopping.server.entity.orders.Order;
import com.sososhopping.server.entity.orders.OrderStatus;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.repository.member.OwnerRepository;
import com.sososhopping.server.repository.order.OrderRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreOrderService {

    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public List<Order> getPendingOrders(Long ownerId, Long storeId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점주입니다"));

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        if (store.getOwner() != owner) {
            throw new Api403Exception("다른 점주의 점포입니다");
        }

        return orderRepository.findPendingOrdersByStore(store);
    }

    @Transactional
    public List<Order> getOrdersByDate(Long ownerId, Long storeId, LocalDate date) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점주입니다"));

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        if (store.getOwner() != owner) {
            throw new Api403Exception("다른 점주의 점포입니다");
        }

        return orderRepository.findOrdersByStoreAndDate(store, date);
    }
}
