package org.delivery.db.userordermenu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.enums.UserOrderMenuState;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "user_order_menu")
public class UserOrderMenuEntity extends BaseEntity {

    @JoinColumn(nullable = false,name = "user_order_id")
    @ManyToOne
    private UserOrderEntity userOrder; // n : 1 (n의 입장에서 가지고있기)

    @JoinColumn(nullable = false) //,name = "store_menu_id")
    @ManyToOne
    private StoreMenuEntity storeMenu; // n : 1 (n의 입장에서 가지고있기)

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserOrderMenuState status;



}
