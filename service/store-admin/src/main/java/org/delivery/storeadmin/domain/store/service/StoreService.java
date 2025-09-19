package org.delivery.storeadmin.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepesitory;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.db.storeuser.StoreUserRepository;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepesitory storeRepesitory;
    // 가게명으로 가게 entity 찾기
    public StoreEntity getStoreByName (String name){
        var entity = storeRepesitory.findFirstByNameAndStatusOrderByIdDesc(name, StoreStatus.REGISTERED);
        return entity.orElseThrow(() -> new RuntimeException("등록된 스토어가 없습니다."));

    }



}
