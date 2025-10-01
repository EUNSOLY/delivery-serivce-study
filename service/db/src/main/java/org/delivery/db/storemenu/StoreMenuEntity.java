package org.delivery.db.storemenu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.enums.StoreMenuStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "store_menu")
public class StoreMenuEntity extends BaseEntity {

    @JoinColumn(nullable = false, name ="store_id") // name 사용안해도 변수명을 기준으로 움직여서 작성안해도됨
    @ManyToOne
    private StoreEntity store;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(precision = 11,scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreMenuStatus status;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;


    private int likeCount;

    private int sequence;


}
