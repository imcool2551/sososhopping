package com.sososhopping.domain.auth.repository;

import com.sososhopping.entity.member.Owner;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerAuthRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByEmail(String email);

    boolean existsByEmail(String email);

    //조인을 통해 가게도 동시에
    @EntityGraph(attributePaths = "stores", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Owner> findOwnerStoresById(Long id);

    Optional<Owner> findByNameAndPhone(String name, String phone);

    Optional<Owner> findByPhone(String phone);
}
