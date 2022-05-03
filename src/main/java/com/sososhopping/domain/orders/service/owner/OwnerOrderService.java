package com.sososhopping.domain.orders.service.owner;

import com.sososhopping.domain.orders.dto.owner.response.OrderListResponse;
import com.sososhopping.domain.orders.repository.OrderRepository;
import com.sososhopping.domain.owner.service.OwnerValidationService;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerOrderService {

    private final OwnerValidationService ownerValidationService;
    private final OrderRepository orderRepository;


    public List<OrderListResponse> findPendingOrders(Long ownerId, Long storeId) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        return orderRepository.findPendingOrders(store)
                .stream()
                .map(order -> new OrderListResponse(order.getUser(), store.getOwner(), order, store))
                .collect(Collectors.toList());
    }

    public List<OrderListResponse> findPickupOrders(Long ownerId, Long storeId, LocalDate date) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        return orderRepository.findTodayPickupOrders(store, date)
                .stream()
                .map(order -> new OrderListResponse(order.getUser(), store.getOwner(), order, store))
                .collect(Collectors.toList());
    }

    public List<OrderListResponse> findDeliveryOrders(Long ownerId, Long storeId, LocalDate date) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        return orderRepository.findTodayDeliveryOrders(store, date)
                .stream()
                .map(order -> new OrderListResponse(order.getUser(), store.getOwner(), order, store))
                .collect(Collectors.toList());
    }

    public List<OrderListResponse> findProcessedOrders(Long ownerId, Long storeId, LocalDate date) {
        Store store = ownerValidationService.validateStoreOwner(ownerId, storeId);

        return orderRepository.findProcessedOrders(store, date)
                .stream()
                .map(order -> new OrderListResponse(order.getUser(), store.getOwner(), order, store))
                .collect(Collectors.toList());
    }
}
