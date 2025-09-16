package org.delivery.db.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

/**
 * 전체 어노테이션 의미
 * "이 클래스는 'account' 테이블과 매핑되는 JPA 엔티티이고,BaseEntity를 상속받으며,
 *  모든 기본 메서드들과 빌더 패턴을 자동 생성해줘. 단, equals/hashCode는 부모 클래스 필드도 포함해서 만들어줘."
 */
@SuperBuilder // 부모로부터 상속받은 변수도 builder에 포함 시키겠다.
@Data // getter, setter, toString, equals, hashCode 등등.. 자동생성
@EqualsAndHashCode(callSuper = true) // 부모 클래스의 필드도 equals, hashCode 비교에 포함 시키겠다.
@Entity // JPA가 이 클래스를 데이터베이스 테이블과 매핑할 엔티티로 인식
@Table(name = "account") // 테이블 연동
public class AccountEntity extends BaseEntity {

}
