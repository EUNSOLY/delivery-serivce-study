package org.delivery.api.config.objectmapper;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
@Configuration
public class ObjectMapperConfig {

    // 스프링부트가 실행될 때 objectMapper에 대한 bean이 정의가 되어있다면 정의 된 objectMapper를 사용하고
    // 안되어있다면 자동으로 기본 objectMapper 생성한다.
    @Bean
    public ObjectMapper objectMapper(){
        var objectMapper = new ObjectMapper();
        // JDK 8 이후 추가된 클래스들(Optional, Stream 등) 직렬화 지원
        objectMapper.registerModule(new Jdk8Module());

        // LocalDate, LocalDateTime 등 Java 8 시간 API 지원
        objectMapper.registerModule(new JavaTimeModule());

        // 모르는 JSON 필드가 있어도 에러내지 않고 무시
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // getter가 없는 빈 객체도 "{}" 빈 JSON으로 직렬화 허용
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 날짜를 타임스탬프 숫자가 아닌 ISO-8601 문자열 형태로 직렬화
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 필드명을 snake_case로 변환 (Java: userName -> JSON: user_name)
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        return objectMapper;

    }
}
*/
