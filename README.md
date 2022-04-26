# sososhop-api

---

sososhop 애플리케이션의 api서버입니다. 
프로토타입에서 V1 로 리팩토링중입니다.


### 1. Refactor List 

---

#### 1.1 아키텍쳐 

* 패키지 구조 수정 
  * 도메인(domain) + 엔티티(entity) + 공통기능(common) 패키지 최상위
    * 도메인마다 각각 controller, service, repository, exception, dto 소유
    
* 서비스 계층이 API 예외를 던지는 대신 도메인에 특화된 예외를 던지도록 수정 (웹 계층과 서비스 계층 분리)

* 예외는 ControllerAdvice 에서 공통 처리

* 엔티티가 dto에 의존하지 않도록 변경

#### 1.2 세부사항

* 사용자 입력값 Bean Validation 을 사용하여 검증 

* ControllerAdvice 에서 콘솔 대신 logger 사용

* 엔티티에서 사용 안하는 양방향 연관관계와 애노테이션 제거.  

* 커맨드와 쿼리 분리

* 이미지 업로드(multipart/form-data)와 JSON 업로드(application/json) 분리 

* 이미지 업로드할 때 prefix, suffix 구분해서 suffix 만 DB에 저장

* 엔티티 매핑시 복합키 대신 유니크 제약 조건 활용

* 일대일 연관관계 주 테이블에 외래키 매핑으로 변경 (Store, StoreMetadata)


### 2. TODOS

---

#### 2.1 SpringSecurity 