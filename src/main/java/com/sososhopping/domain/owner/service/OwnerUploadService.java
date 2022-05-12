package com.sososhopping.domain.owner.service;

import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.common.service.S3Service;
import com.sososhopping.domain.owner.repository.OwnerRepository;
import com.sososhopping.domain.owner.exception.MissingFileException;
import com.sososhopping.entity.owner.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerUploadService {

    private final OwnerRepository ownerRepository;
    private final S3Service s3Service;

    public String upload(Long ownerId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new MissingFileException("missing file");
        }

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);

        String dirPath = owner.getId().toString();
        return s3Service.upload(dirPath, file);
    }
}
