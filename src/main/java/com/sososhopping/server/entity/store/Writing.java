package com.sososhopping.server.entity.store;

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
public class Writing extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "writing_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WritingType writingType;

    @Column(length = 512)
    private String imgUrl;

    // 생성자
//    @Builder
//    public Writing(String title, String content, WritingType writingType, String imgUrl) {
//        this.title = title;
//        this.content = content;
//        this.writingType = writingType;
//        this.imgUrl = imgUrl;
//    }

    // 연관 관계 편의 메서드
    public void setStore(Store store) {
        this.store = store;
        this.store.getWritings().add(this);
    }

    // 비즈니스 로직
    public boolean belongsTo(Store store) {
        return this.store == store;
    }
}
