package org.delivery.api.domain.userordermenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuState;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderMenuService {
    private final UserOrderMenuRepository userOrderMenuRepository;

    public List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId) {
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuState.REGISTERED);
    }


    public UserOrderMenuEntity order(UserOrderMenuEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> {
                            it.setStatus(UserOrderMenuState.REGISTERED);
                            return userOrderMenuRepository.save(it);
                        }
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
