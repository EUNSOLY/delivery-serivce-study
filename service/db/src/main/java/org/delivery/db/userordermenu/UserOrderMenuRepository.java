package org.delivery.db.userordermenu;

import org.delivery.db.userordermenu.enums.UserOrderMenuState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderMenuRepository extends JpaRepository<UserOrderMenuEntity,Long> {

    // 주문 ID에 해당하는 등록된 모든 메뉴 반환
    // select * from user_order_menu where user_order_id = ? And status = ?
    List<UserOrderMenuEntity> findAllByUserOrderIdAndStatus(Long userOrderId, UserOrderMenuState state);

}
