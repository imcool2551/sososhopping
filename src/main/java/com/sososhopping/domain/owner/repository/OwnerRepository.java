package com.sososhopping.domain.owner.repository;

import com.sososhopping.entity.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
