package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.common.api.Api;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreBusiness {
    private final StoreService service;

    private final StoreConverter converter;


    // 등록
    public StoreResponse register(
            StoreRegisterRequest request
    ){
        // req -> entity -> response
        var entity = converter.toEntity(request);
        var newEntity = service.register(entity);
        var response = converter.toResponse(newEntity);

        return response;
    }

    // 카테고리에 맞는 스토어 찾기
    public List<StoreResponse> searchCategory(
            StoreCategory category
    ){
        // entityList => response list
        var storeList = service.searchByCategory(category);
        return storeList.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }

}
