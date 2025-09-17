package org.delivery.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
@RequiredArgsConstructor
@Component     // Spring 컨테이너(Spring Container)에 Bean으로 등록됨 → 다른 곳에서 주입받아 사용 가능
public class AuthorizationInterceptor implements HandlerInterceptor {
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

        // TODO : header 검증


        return  true; // 일단 통과 처리

    }
}
