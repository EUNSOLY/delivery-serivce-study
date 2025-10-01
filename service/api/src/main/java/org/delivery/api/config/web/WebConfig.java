package org.delivery.api.config.web;

import lombok.RequiredArgsConstructor;
import org.delivery.api.interceptor.AuthorizationInterceptor;
import org.delivery.api.resolver.UserSessionResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/*
@Configuration
@RequiredArgsConstructor // Bean 주입
public class WebConfig implements WebMvcConfigurer {
    private final AuthorizationInterceptor authorizationInterceptor;

    private final UserSessionResolver userSessionResolver;

    private List<String> OPEN_API = List.of(
            "/open-api/**"
    );

    private List<String> DEFAULT_EXCLUDE = List.of(
            "/",
            "favicon.ico",
            "/error"
    );

    // 스웨거 검증X (로그인하고 들어오는게 아니기 떄문에)
    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"

    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 주의할점.
        // user가 사용할 수 있는 API는 인증이필요
        // 단, 회원가입, 약관등등 인증을 하면안되는 API가 존재
        // authorizationInterceptor를 등록하면 비회원이라도 인증 로직을 진행되어 에러 발생
        // .excludePathPatterns("/api/user/register") 등으로 인증을 하지않을주소를 입력할수있지만 너무 많아질수 있기 때문에 다른규칙을 정하기
        // open-api는 미 검증 / 그외는 전부 검증

        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(OPEN_API)
                .excludePathPatterns(DEFAULT_EXCLUDE)
                .excludePathPatterns(SWAGGER)
        ;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userSessionResolver);
    }
}
*/
