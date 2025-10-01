package org.delivery.storeadmin.domain.userorder.business;


import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service //Business
public class UserOrderBusiness {
    private final UserOrderService userOrderService;

    private final SseConnectionPool sseConnectionPool;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreMenuService storeMenuService;

    private final StoreMenuConverter storeMenuConverter;

    private final UserOrderConverter userOrderConverter;

    /**
     * 주문
     * 주문내역찾기
     * 스토어 찾기
     * 연결된 세션 찾기
     * 세션에 push
     *
     * @param userOrderMessage
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage) {
        // 사용자 주문이 들어왔음 -> [userOrder entity]

        // 사용자 주문[userOrderEntity]에서 메뉴를 뽑아내기 [userOrderMenu]

        // 메뉴를 가게 메뉴로 변환 [storeMenu]

        // 어떠한메뉴를 주문했는지 반환 [response]

        // push [response]

        // 1. 주문 내역 찾기[사용자 주문]
        var userOrderEntity = userOrderService.getUserOrder(
                        userOrderMessage.getUserOrderId())
                .orElseThrow(() -> new RuntimeException("사용자 주문 내역 없음"));


        // 2. 메뉴 찾기 [userOrderMenuTable 에서 userOrderId를 가지고있는거 다 찾기]
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());


        // 3. 메뉴 리스트로 변환 [store_menu 테이블, storeMenuEntity로 변환]
        // 4. response 형태에 맞게 변환
        var storeMenuResponseList = userOrderMenuList.stream()
                .map(userOrderMenuEntity -> {
                    return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenu().getId());
                }).map(storeMenuConverter::toResponse).toList();

        // 5. userOrderEntity를 Response에 맞게 변환
        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);


        // 6. 메뉴리스트 Response랑 유저오더 Response랑 병합 [상세정보]
        var pushResponse = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build();
        System.out.println("확인 작업 ::::"+pushResponse);


        // 가게와 연결되어있는 (큐에 들어있는)커넥션 찾아오기
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStore().getId().toString());
        System.out.println(sseConnectionPool);
        System.out.println(userConnection + ">>>>>>>>>>");
        userConnection.sendMessage(pushResponse);




    }


}
