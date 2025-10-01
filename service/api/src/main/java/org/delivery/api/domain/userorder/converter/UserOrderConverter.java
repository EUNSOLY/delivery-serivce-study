package org.delivery.api.domain.userorder.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Converter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;

@Converter
@RequiredArgsConstructor
public class UserOrderConverter {

    public UserOrderEntity toEntity(
            User user,
            StoreEntity storeEntity,
            List<StoreMenuEntity> storeMenuEntityList
    ) {
        var totalAmount = storeMenuEntityList.stream()
                .map(it->it.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return UserOrderEntity.builder()
                .userId(user.getId())
                .store(storeEntity)
                .amount(totalAmount)
                .build();
    }

    public UserOrderResponse toResponse(UserOrderEntity entity) {
        return UserOrderResponse.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .amount(entity.getAmount())
                .orderedAt(entity.getOrderedAt())
                .acceptedAt(entity.getAcceptedAt())
                .cookingStartedAt(entity.getCookingStartedAt())
                .deliveryStartedAt(entity.getDeliveryStartedAt())
                .receivedAt(entity.getReceivedAt())
                .build();


    }

}
