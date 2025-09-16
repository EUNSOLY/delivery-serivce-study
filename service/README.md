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
}
 
allprojects {
    repositories {
        mavenCentral()
    }
}
```

```groovy
// build.gradle(:api) 요약버전 

dependencies {
    implementation project(':db') // ✅ db 모듈 의존성 연결
    testImplementation platform('org.junit:junit-bom:5.10.0')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}

```

### 만난 이슈1. 

