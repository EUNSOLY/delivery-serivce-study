package org.delivery.api.domain.userorder.business;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {
    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;


    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;


    private final UserOrderProducer userOrderProducer;
    // 1. 사용자, 메뉴 id
    // 2. userOrder 생성
    // 3. userOrderMenu 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(
            @Valid
            User user,
            UserOrderRequest body
    ) {

        var storeMenuEntityList = body.getStoreMenuIdList()
                .stream()
                .map(storeMenuService::getStoreMenuWithThrow)
                .toList();

        var userOrderEntity = userOrderConverter.toEntity(user,storeMenuEntityList);

        //주문
        var newUserOrderEntity = userOrderService.order(userOrderEntity);

        // 맵핑(user_order_menu)
        var userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it->{
                   // menu + user order
                   var userOrderMenuEntity = userOrderMenuConverter.toEntity(newUserOrderEntity, it);
                   return userOrderMenuEntity;
                }).toList();

        // 주문 내역에 기록 남기기
        userOrderMenuEntityList.forEach(userOrderMenuService::order);

        // 비동기로 가맹점에 주문 알리기
        userOrderProducer.sendOrder(newUserOrderEntity);

        // response
        return  userOrderConverter.toResponse(newUserOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {
        // 현재 사용자가 주문한 내역
        var userOrderEntityList = userOrderService.current(user.getId());

        var userOrderdetailResponseList = userOrderEntityList.stream()
                // 주문 1건씩 처리
                .map(it->{

                    // 사용자가 주문한 메뉴 리스트
                    var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());

                    // 어떠한 메뉴를 주문했는지
                    var storeMenuEntityList = userOrderMenuEntityList.stream()
                            .map(userOrdermenuEntity->{
                                return storeMenuService.getStoreMenuWithThrow(userOrdermenuEntity.getStoreMenuId());

                            }).toList();

                    // 사용자가 주문한 가게
                    // TODO 리팩토링 필요
                    var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());
                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();

                }).toList();


        return userOrderdetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {
        // 현재 사용자가 주문한 내역
        var userOrderEntityList = userOrderService.history(user.getId());

        var userOrderdetailResponseList = userOrderEntityList.stream()
                // 주문 1건씩 처리
                .map(it->{

                    // 사용자가 주문한 메뉴 리스트
                    var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());

                    // 어떠한 메뉴를 주문했는지
                    var storeMenuEntityList = userOrderMenuEntityList.stream()
                            .map(userOrdermenuEntity->{
                                return storeMenuService.getStoreMenuWithThrow(userOrdermenuEntity.getStoreMenuId());

                            }).toList();

                    // TODO 리팩토링 필요
                    // 사용자가 주문한 가게
                    var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());
                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();

                }).toList();


        return userOrderdetailResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId,user.getId());

        var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

        // 어떠한 메뉴를 주문했는지
        var storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrdermenuEntity->{
                    return storeMenuService.getStoreMenuWithThrow(userOrdermenuEntity.getStoreMenuId());

                }).toList();
        // TODO 리팩토링 필요
        // 사용자가 주문한 가게
        var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }
}
