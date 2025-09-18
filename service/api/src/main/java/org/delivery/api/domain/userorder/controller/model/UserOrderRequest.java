package org.delivery.api.domain.userorder.controller.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderRequest {
    // 주문
    // 특정 사용자가, 특정 메뉴를 주문
    // 특정 사용자 = 로그인된 세션에 들어있는 사용자 [값을 받을 필요없음]
    // 특정 메뉴 Id

    @NotNull
    private List<Long> storeMenuIdList;

}
