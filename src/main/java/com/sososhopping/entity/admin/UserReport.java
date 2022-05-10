package com.sososhopping.entity.admin;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.entity.common.AccountStatus;
import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.user.UserLog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_report_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "tinyint")
    private boolean handled = false;

    public UserReport(Store store, User user, String content) {
        this.store = store;
        this.user = user;
        this.content = content;
    }

    public UserLog approve(User user) {
        if (this.user != user) {
            throw new BadRequestException();
        }
        handled = true;
        user.suspend();
        return UserLog.builder()
                .user(user)
                .active(AccountStatus.SUSPEND)
                .description(content)
                .build();
    }

    public UserLog reject(User user) {
        if (this.user != user) {
            throw new BadRequestException();
        }
        handled = true;
        return UserLog.builder()
                .user(user)
                .active(user.getActive())
                .description(content)
                .build();
    }
}