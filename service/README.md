## 프로젝트 내 멀티 모듈 사용하기

### 현재 설정 상태
- 루트 프로젝트의 이름 설정 및 루트프로젝트(부모)가 나에게 포함시킬 프로젝트(자식)을 설정
- 루트에 있는 부모의 build.gradle 파일에 java 프로젝트를 사용하고 내 밑의 모든 레파지토리는 maven을 사용
- 루트 프로젝트 내의 개별의 모듈을 생성하고 각 모듈마다 build.gradle을 가짐
- 자식끼리 종속성을 연결

```groovy
// settings.gradle(service)

rootProject.name = 'service'
include 'api' 
include 'db'
```

```groovy
// build.gradle(service)

plugins {
    id 'java'
    id 'org.springframework.boot' version '3.5.5' // springboot 프로젝트로 변경을 위해 추가
    id 'io.spring.dependency-management' version '1.1.7' // springboot 프로젝트로 변경을 위해 추가
}

 
allprojects {
    repositories {
        mavenCentral()
    }
}

// 루트경로에는 소스코드가 없기 때문에 bootJar, jar로 패키징하지 않겠다는 설정 적용
bootJar{
    enabled= false 
}

jar{
    enabled = false
}
```

```groovy
// build.gradle(:api) 요약버전 

plugins {
    id 'java'
    id 'org.springframework.boot' // 추가 [버전은 부모를 따라가기 때문에 버전 명시X]
    id 'io.spring.dependency-management'
}

group = 'org.delivery'
version = '1.0-SNAPSHOT'

java { // 이 프로젝트를 Java 17로 컴파일하고 실행해주세요 라는 의미로 자바 명시
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

// compileOnly 설정이 annotationProcessor 설정을 상속받아라
// compileOnly : 컴파일 시점에만 필요한 의존성들
// annotationProcessor : 컴파일 시점에만 사용되는 어노테이션 처리기
// lombok으로 어노테이션을 사용할 것 이기 때문에 작성 하기
configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

// 자바프로젝트 -> 스프링부트 프로젝트로 변경에 따른 lombok, springWeb dependencies 적용

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    
    annotationProcessor 'org.projectlombok:lombok'
    compileOnly 'org.projectlombok:lombok'
    
    implementation project(':db')  // db 모듈 의존성 연결
}

test {
    useJUnitPlatform()
}

```


### API 공통 spec 
- API 반환값의 동일한 Spec 설정을 위한 클래스로 아래와 같은 형태로 구성
```json
{
  "result": {
    "result_code": 200,
    "result_message": "OK",
    "result_description": "성공"
  },
  "body": {}
}

```

- 실제 구성 코드
```java
// 최상위 루트
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {
    private Result result;

    @Valid // 유효한지 검사
    private T body;
}

// result
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    private Integer resultCode;

    private String resultMessage;

    private String resultDescription;
}

```
### 프로젝트 작업 중 발생한 이슈
[이슈1. ](https://soly-log.tistory.com/65) <br/>