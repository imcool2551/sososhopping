package com.sososhopping.entity.admin;

import com.sososhopping.common.exception.BadRequestException;
import com.sososhopping.entity.common.BaseTimeEntity;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.StoreLog;
import com.sososhopping.entity.store.StoreStatus;
import com.sososhopping.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.sososhopping.entity.store.StoreStatus.SUSPEND;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreReport extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "store_report_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "tinyint")
    private boolean handled;

    @Builder
    public StoreReport(User user, Store store, String content, boolean handled) {
        this.user = user;
        this.store = store;
        this.content = content;
        this.handled = handled;
    }

    public StoreLog approve(Store store) {
        if (this.store != store) {
            throw new BadRequestException();
        }
        store.updateStoreStatus(SUSPEND);
        handled = true;
        return StoreLog.builder()
                .store(store)
                .storeStatus(StoreStatus.SUSPEND)
                .description(content)
                .build();
    }

    public StoreLog reject(Store store) {
        if (this.store != store) {
            throw new BadRequestException();
        }
        handled = true;
        return StoreLog.builder()
                .store(store)
                .storeStatus(store.getStoreStatus())
                .description(content)
                .build();
    }
}