package org.delivery.storeadmin.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store-user")
@RequiredArgsConstructor
public class StoreUserApiController {
    private final StoreUserConverter storeUserConverter;

    @GetMapping("/me")
    // 스프링시큐리티에서 만들어놓은 AuthenticationPrincipal 사용 (사용자 정보주입)
    public StoreUserResponse me(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserSession userSession
            ){
        return storeUserConverter.toResponse(userSession);
    }

}
