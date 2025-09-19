package org.delivery.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity // security 활성화
public class SecurityConfig {
    private final List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)// 기본값은 활성화
                .authorizeHttpRequests((it) ->
                        it.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()// resource 에 대해서는 모든 요청 허용
                        .requestMatchers(SWAGGER.toArray(new String[0]))
                                .permitAll() // 스웨거는 인증 없이 통과
                                .requestMatchers("/open-api/**").permitAll() // open-api 하위 모든 주소는 인증 없이 통과
                                .anyRequest().authenticated()// 그외 모든 요청은 인증 사용
                )
                .formLogin(Customizer.withDefaults())
                ;
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // hash 방식으로 암호화를 하고 salt(임의의 문자열(랜덤 데이터))를 추가
        return  new BCryptPasswordEncoder();
    }
}
