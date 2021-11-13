package com.sososhopping.server.entity.member;

import com.sososhopping.server.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_log_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountStatus active;

    @Column(columnDefinition = "TEXT")
    private String description;
}