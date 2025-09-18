package org.delivery.api.domain.userordermenu.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

import java.util.List;

@Converter
@RequiredArgsConstructor
public class UserOrderMenuConverter {
    public UserOrderMenuEntity toEntity(
            UserOrderEntity userOrderEntity,
            StoreMenuEntity storeMenuEntity
    ){
        return UserOrderMenuEntity.builder()
                .userOrderId(userOrderEntity.getId())
                .storeMenuId(storeMenuEntity.getId())
                .build();
    }
}
