package com.sososhopping.server.service.owner;

import com.sososhopping.server.common.dto.owner.request.StoreBusinessDayRequestDto;
import com.sososhopping.server.common.dto.owner.request.StoreRequestDto;
import com.sososhopping.server.common.dto.owner.response.StoreListResponseDto;
import com.sososhopping.server.common.dto.owner.response.StoreResponseDto;
import com.sososhopping.server.common.error.Api400Exception;
import com.sososhopping.server.common.error.Api500Exception;
import com.sososhopping.server.entity.member.Owner;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.StoreBusinessDay;
import com.sososhopping.server.entity.store.StoreMetaData;
import com.sososhopping.server.repository.member.OwnerRepository;
import com.sososhopping.server.repository.store.StoreRepository;
import com.sososhopping.server.service.common.S3Service;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerStoreService {

    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;
    private final EntityManager em;
    private final S3Service s3Service;

    //점주의 점포 리스트 조회
    @Transactional
    public List<Store> readStoreList(Long ownerId) {
        Owner owner = ownerRepository.findOwnerStoresById(ownerId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점주입니다"));

        return owner.getStores();
    }

    //점주의 특정 점포 조회
    @Transactional
    public Store readStore(Long storeId) {
        return storeRepository.findStoreInforById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));
    }


    //점주의 점포 생성
    @Transactional
    public void createStore(StoreRequestDto dto, Long ownerId, MultipartFile image) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점주입니다"));

        /**
         * 위, 경도 타입 변환 작업
         */
        String pointWKT = "POINT(" + dto.getLng() + " " + dto.getLat() + ")";
        Point location;
        try {
            location = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new Api500Exception("좌표 변환 중 오류가 발생했습니다");
        }

        Store store = new Store(owner, dto, location);
        em.persist(store);

        StoreMetaData storeMetaData = new StoreMetaData(store, dto.getStoreMetaDataResponseDto());
        em.persist(storeMetaData);

        List<StoreBusinessDayRequestDto> days = dto.getStoreBusinessDays();
        for (StoreBusinessDayRequestDto day : days) {
            em.persist(new StoreBusinessDay(store, day));
        }

        /**
         * 이미지 저장
         */
        if (image != null && !image.isEmpty()) {
            try {
                String imgUrl = s3Service.upload(image, "store/" + store.getId());
                store.setImgUrl(imgUrl);
            } catch (IOException e) {
                throw new Api500Exception("이미지 저장에 실패했습니다");
            }
        }
    }

    //점포 수정
    @Transactional
    public void updateStore(Long storeId, StoreRequestDto dto, MultipartFile image) {
        Store store = storeRepository.findStoreInforById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        store.update(dto);

        if (image != null && !image.isEmpty()) {
            try {//이미지 저장 후 url 교체
                String newImgUrl = s3Service.upload(image, "store/" + store.getId());
//                String deleteImgUrl = store.getImgUrl().substring(store.getImgUrl().lastIndexOf("/"));
//                s3Service.delete("store/" + store.getId() + "/" + deleteImgUrl);
                store.setImgUrl(newImgUrl);
            } catch (IOException e) {
                throw new Api500Exception("이미지 저장에 실패했습니다");
            }
        }
    }

    //점포 삭제 (등록 미승인 점포 한정)
    @Transactional
    public void deleteStore(Long storeId) {
        Store store = storeRepository.findStoreInforById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        if (store.getStoreStatus().name().equals("PENDING")) {
            storeRepository.deleteById(storeId);
        } else {
            throw new Api400Exception("승인 대기중인 점포가 아닙니다");
        }
    }
}
