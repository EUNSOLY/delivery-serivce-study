package org.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;

import java.util.List;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    // 등록 로직
    public StoreMenuResponse register(
            StoreMenuRegisterRequest request
    ){
        // req -> entity -> save -> response
        var entity = storeMenuConverter.toEntity(request);
        var newEntity = storeMenuService.register(entity);
        var response = storeMenuConverter.toResponse(newEntity);
        return response;
    };

    //가게 메뉴 검색
    public List<StoreMenuResponse> searchStoreMenu(
            Long storeId
    ){
        var list = storeMenuService.getStoreMenuByStoreId(storeId);

        var newList = list.stream()
                .map(storeMenuConverter::toResponse)
                .toList();

        return newList;

    }

}
