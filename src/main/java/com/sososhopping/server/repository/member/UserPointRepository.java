package com.sososhopping.server.repository.member;

import com.sososhopping.server.entity.member.UserPoint;
import com.sososhopping.server.entity.member.UserPointId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointRepository extends JpaRepository<UserPoint, UserPointId> {

}
