package com.sososhopping.entity.report;

import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
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
    private boolean handled;

    @Builder
    public UserReport(Store store, User user, String content, boolean handled) {
        this.store = store;
        this.user = user;
        this.content = content;
        this.handled = handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }
}