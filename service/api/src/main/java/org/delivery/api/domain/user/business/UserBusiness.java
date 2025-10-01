package org.delivery.api.domain.user.business;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.common.annotation.Business;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;

    private final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;


    /**
     * 사용자에 대한 가입처리 로직
     * 1. request -> entity
     * 2. entity -> save(저장)
     * 3. save Entity -> response(반환)
     * 4. response return
     * @param request
     * @return
     */
    public UserResponse register(@Valid UserRegisterRequest request) {
        var entity = userConverter.toEntity(request);
        var newEntity = userService.register(entity);
        return userConverter.toResponse(newEntity);
        /**
         * 위에 코드를 람다식으로 변경하면
         * return Optional.ofNullable(request)
         *                 .map(userConverter::toEntity)
         *                 .map(userService::register)
         *                 .map(userConverter::toResponse)
         *                 .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"등록할 회원정보가 존재하지 않습니다."));
         */
    }

    /**
     * 1. email, password를 가지고 유효한 사용자 있는지 체크
     * 2. 확인이 된 user Entity로 로그인확인
     * 3. token 생성
     * 4. token response
     * @param body
     */
    public TokenResponse login(@Valid UserLoginRequest request) {
        var userEntity = userService.login(request.getEmail(),request.getPassword());
        // 사용자가 없으면 throw 발생 했음

        // 사용자가 있을 때
        return tokenBusiness.issueToken(userEntity);

    }

    public UserResponse me(
            User user
    ) {
        return userConverter.toResponse(user);
    }

}


