package com.sososhopping.entity.owner;

import com.sososhopping.common.dto.owner.request.StoreWritingRequestDto;
import com.sososhopping.entity.common.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Writing extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "writing_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @Enumerated(EnumType.STRING)
    private WritingType writingType;

    private String imgUrl;

    @Builder
    public Writing(Store store, String title, String content, WritingType writingType) {
        this.store = store;
        this.title = title;
        this.content = content;
        this.writingType = writingType;
    }

    // 비즈니스 로직
    public boolean belongsTo(Store store) {
        return this.store == store;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void update(StoreWritingRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.writingType = dto.getWritingType();
    }
}
