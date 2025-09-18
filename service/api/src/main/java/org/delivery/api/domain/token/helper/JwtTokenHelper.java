package org.delivery.api.domain.token.helper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperInterface;
import org.delivery.api.domain.token.model.TokenDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperInterface {


    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    // 디버깅용 추가
    @PostConstruct
    public void init() {
        System.out.println("=== JWT Token Helper 초기화 ===");
        System.out.println("secretKey: " + secretKey);
        System.out.println("accessTokenPlusHour: " + accessTokenPlusHour);
        System.out.println("refreshTokenPlusHour: " + refreshTokenPlusHour);
    }
    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {

        // 만료 시간 설정(현재시간에서 accessTokenPlusHour 시간만큼 더 한 값으로 설정)
        var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);

        // LocalDateTime -> Date 로 변환 (JWT가 Date Type을 요구하기에 변환필요)
        var expiredAt = Date.from(
                expiredLocalDateTime
                        .atZone(ZoneId.systemDefault()) // 시스템 기본 시간대 적용
                        .toInstant() // 인스턴스로 변환
        );

        // 서명 키 생성
        // Jwt 서명용 암호화 키 생성
        // 1. secretKey.getBytes(): 문자열 비밀키를 바이트 배열로 변환
        // 2. Keys.hmacShaKeyFor(): HMAC-SHA 알고리즘용 키 객체 생성
        var key = Keys.hmacShaKeyFor(secretKey.getBytes()); //


        //JWT TOKEN 생성
        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘과 키 지정
                .setClaims(data) // 토큰에 담을 데이터 저장
                .setExpiration(expiredAt) // 토큰 만료 시간 설정
                .compact(); // JWT 형태의 문자열로 최종 변환

        // 응답 객체 생성
        return TokenDto.builder()
                .token(jwtToken) // 생성된 JWT 토큰
                .expiredAt(expiredLocalDateTime) // 만료 시간
                .build();
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        // 만료 시간 설정
        var expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);

        // LocalDateTime -> Date 로 변환
        var expiredAt = Date.from(
                expiredLocalDateTime
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        // 서명 키 생성
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());


        //JWT TOKEN 생성
        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        // 응답 객체 생성
        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();


        try{
            var result =  parser.parseClaimsJws(token);

            return new HashMap<String, Object>(result.getBody());

        } catch (Exception e) {
            if(e instanceof SignatureException){
                // 토큰이 유효하지 않을 때
                throw new ApiException(TokenErrorCode.INVALID_TOKEN,e);

            }else if(e instanceof ExpiredJwtException){
                // 만료된 토큰일 때
                throw new ApiException(TokenErrorCode.EXPIRED_FOUNDK,e);
            }else {
                // 그외 예외
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION,e);
            }
        }
    }
}
