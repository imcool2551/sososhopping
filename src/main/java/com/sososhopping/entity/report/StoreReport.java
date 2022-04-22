package com.sososhopping.entity.report;

import com.sososhopping.entity.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.member.User;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Builder
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreReport extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_report_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Type(type = "numeric_boolean")
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean handled;

    public void setHandled(boolean handled) {
        this.handled = handled;
    }
}