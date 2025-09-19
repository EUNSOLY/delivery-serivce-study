package org.delivery.storeadmin.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.common.annotation.Business;
import org.delivery.storeadmin.domain.store.service.StoreService;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreUserBusiness {
    private final StoreUserService storeUserService;
    private final StoreUserConverter storeUserConverter;
    private final StoreService storeService;

    public StoreUserResponse register(
            StoreUserRegisterRequest request
    ) {
        var storeEntity = storeService.getStoreByName(request.getStoreName());
        var entity = storeUserConverter.toEntity(request, storeEntity);

        var newEntity = storeUserService.register(entity);

        var response = storeUserConverter.toResponse(newEntity,storeEntity);

        return response;
    }
}
