package org.delivery.api.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.user.business.UserBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    private final UserBusiness userBusiness;
}

/**
 * 요청 흐름 : client -> controller -> business -> service -> repository -> db
 * 응답 흐름 : client <- controller <- business <- service <- repository <- db
 *
 * business와 service를 분리하는 이유는 여러 service를 조합해야할 수 도 있기 때문에 service는 자신의 도메인로직만 처리하기위해서
 * 복잡한 로직(다른 도메인의 서비스를 호출하여 조합등)은 business로 분리해서 사용
 */

