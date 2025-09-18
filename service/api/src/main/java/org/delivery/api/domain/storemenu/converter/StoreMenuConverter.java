package org.delivery.api.domain.storemenu.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class StoreMenuConverter {

    public StoreMenuEntity toEntity(
            StoreMenuRegisterRequest storeMenuRequest
    ){
     return Optional.ofNullable(storeMenuRequest)
             .map(it->{
                 return StoreMenuEntity.builder()
                         .storeId(it.getStoreId())
                         .name(it.getName())
                         .amount(it.getAmount())
                         .thumbnailUrl(it.getThumbnailUrl())
                         .build();
             }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreMenuResponse toResponse(
            StoreMenuEntity storeMenuEntity
    ){
        return Optional.ofNullable(storeMenuEntity)
                .map(it->{
                    return StoreMenuResponse.builder()
                            .id(it.getId())
                            .storeId(it.getStoreId())
                            .name(it.getName())
                            .amount(it.getAmount())
                            .status(it.getStatus())
                            .thumbnailUrl(it.getThumbnailUrl())
                            .likeCount(it.getLikeCount())
                            .sequence(it.getSequence())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public List<StoreMenuResponse> toResponse(
            List<StoreMenuEntity> list
    ){
        return list.stream().map(this::toResponse).toList();
    }

}
