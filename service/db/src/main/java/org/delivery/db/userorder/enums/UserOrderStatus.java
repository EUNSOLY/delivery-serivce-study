package org.delivery.db.userorder.enums;

// 어노테이션 사용안하고 진행 시;
public enum UserOrderStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ORDERED("주문"),
    ACCEPT("확인"),
    COOKING("요리중"),
    DELIVERY("배달중"),
    RECEIVE("완료");
    ;

    UserOrderStatus(String description) {
        this.description = description;
    }

    private String description;
}
