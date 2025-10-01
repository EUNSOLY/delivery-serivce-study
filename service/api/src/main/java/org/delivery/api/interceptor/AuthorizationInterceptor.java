package org.delivery.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.TokenErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component     // Spring 컨테이너(Spring Container)에 Bean으로 등록됨 → 다른 곳에서 주입받아 사용 가능
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final TokenBusiness tokenBusiness;


    // 사전 검증 구간
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        //Web, chrome의 경우 GET, POST 전 OPTIONS를 호출하여 해당 메서드를 지원하는지 체크하는 API가 존재하기에 이 API 통과시키기 필요
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        // 리소스 검증(API요청이 아닌 JS, HTML, PNG 등을 받아가는 요청이라면 통과)
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        // header 토큰 검증
        var accessToken = request.getHeader("authorization-token");
        if(accessToken ==null){
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        var userId =  tokenBusiness.validationAccessToken(accessToken);

        if(userId !=null){
            // 한가지 요청에 대해서 유효한값을 글로벌하게 저장할 수있는 저장소에 저장
            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute("userId",userId, RequestAttributes.SCOPE_REQUEST);
            return true;
        }

        throw new ApiException(ErrorCode.BAD_REQUEST,"인증실패");

    }
}
