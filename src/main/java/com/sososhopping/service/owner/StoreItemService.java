package com.sososhopping.service.owner;

import com.sososhopping.common.dto.owner.request.StoreItemRequestDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api403Exception;
import com.sososhopping.common.error.Api500Exception;
import com.sososhopping.entity.store.Item;
import com.sososhopping.entity.store.Store;
import com.sososhopping.repository.order.CartRepository;
import com.sososhopping.repository.order.OrderItemRepository;
import com.sososhopping.repository.store.ItemRepository;
import com.sososhopping.repository.store.StoreRepository;
import com.sososhopping.service.common.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreItemService {

    private final StoreRepository storeRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final S3Service s3Service;
    private final EntityManager em;

    @Transactional
    public List<Item> readItemList(Long storeId) {
        Store store = storeRepository.findStoreWithItemById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        return store.getItems();
    }

    @Transactional
    public void createItem(Long storeId, StoreItemRequestDto dto
            , MultipartFile image) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        Item item = Item.builder()
                .store(store)
                .name(dto.getName())
                .description(dto.getDescription())
                .purchaseUnit(dto.getPurchaseUnit())
                .price(dto.getPrice())
                .saleStatus(dto.getSaleStatus())
                .build();

        em.persist(item);

        if (image != null && !image.isEmpty()) {
            try {
                String imgUrl = s3Service.upload(image, "store/" + storeId + "/item/" + item.getId());
                item.setImgUrl(imgUrl);
                // 이전 url 삭제 기능 미구현
            } catch (IOException e) {
                throw new Api500Exception("이미지 저장에 실패했습니다");
            }
        }
    }

    @Transactional
    public Item readItem(Long storeId, Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 물품입니다"));
    }

    @Transactional
    public void updateItem(Long storeId, Long itemId
            , StoreItemRequestDto dto, MultipartFile image) {
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 물품입니다"));

        item.update(dto);

        if (image != null && !image.isEmpty()) {
            try {
                String newImgUrl = s3Service.upload(image, "store/" + storeId + "/item/" + item.getId());
                item.setImgUrl(newImgUrl);
                // 이전 url 삭제 기능 미구현
            } catch (IOException e) {
                throw new Api500Exception("이미지 저장에 실패했습니다");
            }
        }
    }

    @Transactional
    public void deleteItem(Long storeId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 물품입니다"));

        if (orderItemRepository.existsByItem(item)) {
            throw new Api403Exception("현재 고객의 주문 목록 중에 해당 물품이 존재합니다");
        }

        if (cartRepository.existsByItem(item)) {
            cartRepository.deleteByItem(item);
        }

        itemRepository.deleteById(itemId);
    }
}
