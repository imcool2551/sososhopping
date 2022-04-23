package com.sososhopping.domain.owner.service;

import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.owner.dto.request.OwnerInfoUpdateDto;
import com.sososhopping.domain.owner.repository.OwnerRepository;
import com.sososhopping.entity.owner.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerInfoService {

    private final OwnerRepository ownerRepository;

    public Long updateOwnerInfo(Long ownerId, OwnerInfoUpdateDto dto) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);

        owner.updateName(dto.getName());
        return owner.getId();
    }
}
