package org.delivery.api.domain.userorder.controller.model;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserOrderRequest {
    @NotNull
    private Long storeId;
    // 주문
    // 특정 사용자가, 특정 메뉴를 주문
    // 특정 사용자 = 로그인된 세션에 들어있는 사용자 [값을 받을 필요없음]
    // 특정 메뉴 Id
    @NotNull
    private List<Long> storeMenuIdList;

    public Long getStoreId() {
        return storeId;
    }

    public List<Long> getStoreMenuIdList() {
        return storeMenuIdList;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setStoreMenuIdList(List<Long> storeMenuIdList) {
        this.storeMenuIdList = storeMenuIdList;
    }
}
