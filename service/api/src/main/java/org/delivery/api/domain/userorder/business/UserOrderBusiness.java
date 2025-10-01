package org.delivery.api.domain.userorder.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.annotation.Business;
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
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.enums.UserOrderMenuState;

import java.util.List;
import java.util.stream.Collectors;

/*
@Business
@RequiredArgsConstructor
@Slf4j
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
    private final ObjectMapper objectMapper;


    // 1. 사용자, 메뉴 id
    // 2. userOrder 생성
    // 3. userOrderMenu 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(
            @Valid
            User user,
            UserOrderRequest body
    ) {
        var storeEntity = storeService.getStoreWithThrow(body.getStoreId());

        var storeMenuEntityList = body.getStoreMenuIdList()
                .stream()
                .map(storeMenuService::getStoreMenuWithThrow)
                .toList();

        var userOrderEntity = userOrderConverter.toEntity(
                user,
                storeEntity,
                storeMenuEntityList);

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
                .map(userOrderEntity->{
                    log.info("사용자의 주문 정보 : {}", userOrderEntity);
          */
/*          try {
                        var jsonString = objectMapper.writeValueAsString(userOrderEntity);
                        log.info("Json String : {}", jsonString);

                    }catch (Exception e){
                        log.error("",e);
                    }*//*


                    // 사용자가 주문한 메뉴 리스트
                    // 기존 : var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
                    // 엔티티 연관관계 설정 후 리펙토링
                    var userOrderMenuEntityList = userOrderEntity.getUserOrderMenuList().stream()
                            .filter(it-> it.getStatus().equals(UserOrderMenuState.REGISTERED))
                            .toList();

                    // 어떠한 메뉴를 주문했는지

                    */
/* var storeMenuEntityList = userOrderMenuEntityList.stream()
                            .map(userOrdermenuEntity->{
                                return storeMenuService.getStoreMenuWithThrow(userOrdermenuEntity.getStoreMenu().getId());
                            }).toList();*//*


                    // 연관관계 설정 후 리펙토링
                    var storeMenuEntityList = userOrderMenuEntityList.stream()
                            .map(UserOrderMenuEntity::getStoreMenu).toList();

                    // 사용자가 주문한 가게
                    // var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStore().getId());
                    // 연관관계 설정 후 리펙토링
                    var storeEntity = userOrderEntity.getStore();

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
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
                .map(userOrderEntity->{

                    // 사용자가 주문한 메뉴 리스트
                    var userOrderMenuEntityList = userOrderEntity.getUserOrderMenuList();

                    // 어떠한 메뉴를 주문했는지
                    var storeMenuEntityList = userOrderMenuEntityList.stream()
                            .map(UserOrderMenuEntity::getStoreMenu).toList();

                    // 사용자가 주문한 가게
                    var storeEntity =  userOrderEntity.getStore();
                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();

                }).toList();


        return userOrderdetailResponseList;
    }

    // 사용자 주문 메뉴 상세
    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId,user.getId());

        var userOrderMenuEntityList = userOrderEntity.getUserOrderMenuList().stream()
                .filter(it -> it.getStatus().equals(UserOrderMenuState.REGISTERED))
                .toList();

        // 어떠한 메뉴를 주문했는지
        var storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(UserOrderMenuEntity::getStoreMenu).toList();

        // 사용자가 주문한 가게
        var storeEntity = userOrderEntity.getStore();

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }
}
*/
