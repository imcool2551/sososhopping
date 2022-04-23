package com.sososhopping.domain.auth.repository;

import com.sososhopping.entity.owner.Owner;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerAuthRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    //조인을 통해 가게도 동시에
    @EntityGraph(attributePaths = "stores", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Owner> findOwnerStoresById(Long id);

    Optional<Owner> findByPhone(String phone);
}
