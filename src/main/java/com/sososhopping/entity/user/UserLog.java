package com.sososhopping.entity.user;

import com.sososhopping.entity.common.AccountStatus;
import com.sososhopping.entity.common.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class UserLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_log_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AccountStatus active;

    @Column(columnDefinition = "text")
    private String description;

    @Builder
    public UserLog(User user, AccountStatus active, String description) {
        this.user = user;
        this.active = active;
        this.description = description;
    }
}