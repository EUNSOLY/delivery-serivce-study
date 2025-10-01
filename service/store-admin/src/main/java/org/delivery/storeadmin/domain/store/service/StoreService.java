package org.delivery.storeadmin.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepesitory;
    // 가게명으로 가게 entity 찾기
    public StoreEntity getStoreByName (String name){
        var entity = Optional.ofNullable(storeRepesitory.findFirstByNameAndStatusOrderByIdDesc(name, StoreStatus.REGISTERED));
        return entity.orElseThrow(() -> new RuntimeException("등록된 스토어가 없습니다."));
    }

    public StoreEntity getStoreWithThrow(Long id){
        var entity = Optional.ofNullable(storeRepesitory.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED));
        return entity.orElseThrow(()-> new RuntimeException("유효한 스토어가 없습니다."));
    }






}
