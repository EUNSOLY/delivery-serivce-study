package org.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지"),
    PENDING("대기"),
    CANCELLATION_REQUESTED("해지신청");


    ;
    private String description;
}
