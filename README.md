# sososhop-api

---

sososhop 애플리케이션의 api서버입니다. 
프로토타입에서 V1 로 리팩토링중입니다.


### 1. Refactor List 

---

#### 1.1 아키텍쳐 레벨

* 패키지 구조 수정 
  * 도메인(domain) + 엔티티(entity) + 공통기능(common) 패키지 최상위
    * 도메인 패키지밑에 도메인 별로 controller, service, repository
    
* 서비스 계층이 API 예외를 던지는 대신 도메인에 특화된 예외를 던지도록 수정 (웹 계층과 서비스 계층 분리)

* 예외는 ControllerAdvice 에서 공통 처리

#### 1.2 세부사항

* 사용자 입력값 Bean Validation 을 사용하여 검증 

* 성능을 고려하여 ControllerAdvice 에서 콘솔 대신 logger 를 사용

* 엔티티에서 사용 안하는 양방향 연관관계와 애노테이션 제거.  

* 커맨드와 쿼리 분리



### 2. TODOS

---

#### 2.1 SpringSecurity 