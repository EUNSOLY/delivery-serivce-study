package org.delivery.db.userorder.enums;

// 어노테이션 사용안하고 진행 시;
public enum UserOrderStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    UserOrderStatus(String description) {
        this.description = description;
    }

    private String description;
}
