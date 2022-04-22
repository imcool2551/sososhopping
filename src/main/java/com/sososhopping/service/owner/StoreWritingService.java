package com.sososhopping.service.owner;

import com.sososhopping.common.dto.owner.request.StoreWritingRequestDto;
import com.sososhopping.common.error.Api400Exception;
import com.sososhopping.common.error.Api500Exception;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.Writing;
import com.sososhopping.repository.store.StoreRepository;
import com.sososhopping.repository.store.WritingRepository;
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
public class StoreWritingService {

    private final StoreRepository storeRepository;
    private final WritingRepository writingRepository;
    private final S3Service s3Service;
    private final EntityManager em;

    @Transactional
    public List<Writing> readWritingList(Long storeId) {
        Store store = storeRepository.findStoreWithWritingById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        return store.getWritings();
    }

    @Transactional
    public void createWriting(Long storeId, StoreWritingRequestDto dto
            , MultipartFile image) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 점포입니다"));

        Writing writing = Writing.builder()
                .store(store)
                .title(dto.getTitle())
                .content(dto.getContent())
                .writingType(dto.getWritingType())
                .build();

        em.persist(writing);

        if (image != null && !image.isEmpty()) {
            try {
                String imgUrl = s3Service.upload(image, "store/" + storeId + "/writing/" + writing.getId());
                writing.setImgUrl(imgUrl);
                // 이전 url 삭제 기능 미구현
            } catch (IOException e) {
                throw new Api500Exception("이미지 저장에 실패했습니다");
            }
        }
    }

    @Transactional
    public Writing readWriting(Long storeId, Long writingId) {
        return writingRepository.findById(writingId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 글입니다"));
    }

    @Transactional
    public void updateWriting(Long storeId, Long writingId
            , StoreWritingRequestDto dto, MultipartFile image) {
        Writing writing = writingRepository.findById(writingId).orElseThrow(() ->
                new Api400Exception("존재하지 않는 글입니다"));

        writing.update(dto);

        if (image != null && !image.isEmpty()) {
            try {
                String imgUrl = s3Service.upload(image, "store/" + storeId + "/writing/" + writing.getId());
                writing.setImgUrl(imgUrl);
                // 이전 url 삭제 기능 미구현
            } catch (IOException e) {
                throw new Api500Exception("이미지 저장에 실패했습니다");
            }
        }
    }

    @Transactional
    public void deleteWriting(Long storeId, Long writingId) {
        writingRepository.deleteById(writingId);
    }

}
