package com.sososhopping.server.entity.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.sososhopping.server.entity.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Writing extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "writing_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WritingType writingType;

    private String imgUrl;
}
