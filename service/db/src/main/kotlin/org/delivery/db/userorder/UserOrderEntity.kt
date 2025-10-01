package org.delivery.db.userorder

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.delivery.db.store.StoreEntity
import org.delivery.db.userorder.enums.UserOrderStatus
import org.delivery.db.userordermenu.UserOrderMenuEntity
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "user_order")
/**
 * 코틀린의 경우 private로 하면 자동 캡슐화가 되어 getter, setter가 제공되지 않음
 * 프로퍼티로 제공되기 때문에 변수로 선언
 * Null에 대한 안정성이 있기 때문에 값이 없다면 기본 Null로 설정 진행
 * 기본을 Null로 한다해도 DB 설정을 Nullable로 해놨기 때문에 Null값이 안된다는 오류 발생하니까 참고
 * data class 는 hash, equals, toString,copy 등등을 제공함 그래서 JPA의 엔티티설정과는 궁합이 좋지않다고 판단됨 그래서 사용X
 * override를 통해서 필요한건 직접구현해서 사용하는게 효율적(자바보단 손이 가는느낌)
 */
class UserOrderEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:Column(nullable = false)
    var userId: Long? = null,

    @field:JoinColumn(nullable = false, name = "store_id")
    @field:ManyToOne
    var store: StoreEntity? = null,

    @field:Enumerated(EnumType.STRING)
    @field:Column(length = 50, nullable = false)
    var status: UserOrderStatus? = null,

    @field:Column(precision = 11, scale = 4, nullable = false)
    var amount: BigDecimal? = null,

    var orderedAt: LocalDateTime? = null,

    var acceptedAt: LocalDateTime? = null,

    var cookingStartedAt: LocalDateTime? = null,

    var deliveryStartedAt: LocalDateTime? = null,

    var receivedAt: LocalDateTime? = null,

    @field:OneToMany(mappedBy = "userOrder")
    @field:JsonIgnore
    var userOrderMenuList : MutableList<UserOrderMenuEntity>? =null

) {

    override fun toString(): String {
        return "UserOrderEntity(id=$id, userId=$userId, store=$store, status=$status, amount=$amount, orderedAt=$orderedAt, acceptedAt=$acceptedAt, cookingStartedAt=$cookingStartedAt, deliveryStartedAt=$deliveryStartedAt, receivedAt=$receivedAt)"
    }
}