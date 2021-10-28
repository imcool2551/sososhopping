package com.sososhopping.server.entity.member.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.member.AccountStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLog extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_log_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountStatus active;

    private String description;
}
