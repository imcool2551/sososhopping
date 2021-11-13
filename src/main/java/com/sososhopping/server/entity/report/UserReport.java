package com.sososhopping.server.entity.report;

import com.sososhopping.server.entity.BaseTimeEntity;
import com.sososhopping.server.entity.member.User;
import com.sososhopping.server.entity.store.Store;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_report_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Type(type = "numeric_boolean")
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean handled;

    public UserReport(Store store, User user, String content, Boolean handled) {
        this.store = store;
        this.user = user;
        this.content = content;
        this.handled = handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }
}