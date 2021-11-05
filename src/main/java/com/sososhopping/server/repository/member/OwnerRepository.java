package com.sososhopping.server.repository.member;

import com.sososhopping.server.entity.member.Owner;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    //이메일로 정보 찾기
    Optional<Owner> findByEmail(String email);

    //이메일 중복 확인
    boolean existsByEmail(String email);

    //조인을 통해 가게도 동시에
    @EntityGraph(attributePaths = "stores", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Owner> findOwnerStoresById(Long id);

}
