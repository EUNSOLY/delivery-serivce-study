package org.delivery.api.domain.userorder.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserOrderConverter {

    public UserOrderEntity toEntity(
            User user,
            Long storeId,
            List<StoreMenuEntity> storeMenuEntityList
    ) {
        var totalAmount = storeMenuEntityList.stream()
                .map(it->it.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return UserOrderEntity.builder()
                .userId(user.getId())
                .storeId(storeId)
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
