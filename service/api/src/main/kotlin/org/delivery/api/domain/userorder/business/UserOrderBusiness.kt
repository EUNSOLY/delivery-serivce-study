package org.delivery.api.domain.userorder.business

import org.delivery.api.common.Log
import org.delivery.api.domain.store.converter.StoreConverter
import org.delivery.api.domain.store.service.StoreService
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter
import org.delivery.api.domain.storemenu.service.StoreMenuService
import org.delivery.api.domain.user.model.User
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse
import org.delivery.api.domain.userorder.converter.UserOrderConverter
import org.delivery.api.domain.userorder.producer.UserOrderProducer
import org.delivery.api.domain.userorder.service.UserOrderService
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService
import org.delivery.common.annotation.Business
import org.delivery.db.userordermenu.enums.UserOrderMenuState

@Business
class UserOrderBusiness(
    private val userOrderService: UserOrderService,
    private val userOrderConverter: UserOrderConverter,

    private val storeMenuService: StoreMenuService,
    private val storeMenuConverter: StoreMenuConverter,

    private val userOrderMenuService: UserOrderMenuService,
    private val userOrderMenuConverter: UserOrderMenuConverter,

    private val storeService: StoreService,
    private val storeConverter: StoreConverter,

    private val userOrderProducer: UserOrderProducer

) {
    companion object : Log

    fun userOrder(
        user: User?,
        body: UserOrderRequest?
    ): UserOrderResponse {
        // 가게 찾기
        val storeEntity = storeService.getStoreWithThrow(body?.storeId)

        // 주문한 메뉴 찾기
        val storeMenuEntityList = body?.storeMenuIdList
            ?.mapNotNull { it -> storeMenuService.getStoreMenuWithThrow(it) }
            ?.toList()

        // 주문하고나서의 반환값
        val userOrderEntity = userOrderConverter.toEntity(
            user = user,
            storeEntity = storeEntity,
            storeMenuEntityList = storeMenuEntityList
        ).run {
            //주문
            userOrderService.order(this)
        }

        // 맵핑[user order menu list 생성]
        val userOrderMenuEntityList = storeMenuEntityList
            ?.map { it -> userOrderMenuConverter.toEntity(userOrderEntity, it) }
            ?.toList()

        // 주문내역 기록 남기기
        userOrderMenuEntityList?.forEach { userOrderMenuService.order(it) }

        // 비동기 주문 알리기
        userOrderProducer.sendOrder(userOrderEntity)

        return userOrderConverter.toResponse(userOrderEntity)
    }

    fun current(
        user: User?
    ): List<UserOrderDetailResponse>? {
        return userOrderService.current(user?.id).map { userOrderEntity ->
            /*  val userOrderMenuEntityList = it.userOrderMenuList
                ?.filter {  it.status == UserOrderMenuState.REGISTERED }
                ?.toList()

                val storeMenuEntityList = userOrderMenuEntityList
                ?.map {it.storeMenu}
                ?.toList()
           */

            val storeMenuEntityList = userOrderEntity.userOrderMenuList?.stream()
                ?.filter { userOrderMenuEntity -> userOrderMenuEntity.status === UserOrderMenuState.REGISTERED }
                ?.map {
                    it.storeMenu
                }?.toList()


            val store = userOrderEntity.store
            UserOrderDetailResponse(
                userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                storeResponse = storeConverter.toResponse(store),
                storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
            )
        }.toList()
    }

    fun history(
        user: User?
    ): List<UserOrderDetailResponse>? {
        return userOrderService.history(user?.id).map { userOrderEntity ->
            val storeMenuEntityList = userOrderEntity.userOrderMenuList?.stream()
                ?.filter { userOrderMenuEntity -> userOrderMenuEntity.status === UserOrderMenuState.REGISTERED }
                ?.map {
                    it.storeMenu
                }?.toList()


            val store = userOrderEntity.store
            UserOrderDetailResponse(
                userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                storeResponse = storeConverter.toResponse(store),
                storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
            )
        }.toList()
    }

    fun read(
        user: User?,
        orderId: Long?

    ): UserOrderDetailResponse {
        return userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user?.id)
            .let { userOrderEntity ->
                val storeMenuEntityList = userOrderEntity.userOrderMenuList
                    ?.stream()
                    ?.filter { it.status == UserOrderMenuState.REGISTERED }
                    ?.map{it.storeMenu}
                    ?.toList() // 있거나
                    ?:listOf()  // size 0

                UserOrderDetailResponse(
                    userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                    storeResponse = storeConverter.toResponse(userOrderEntity.store),
                    storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
                )

            }

    }

}