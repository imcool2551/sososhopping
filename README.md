# sososhop-api

---

sososhop 애플리케이션의 api서버입니다. 
프로토타입에서 V1 로 리팩토링중입니다.


### 1. Refactor List 

---

#### 1.1 아키텍쳐 

* 패키지 구조 수정 
  * 도메인(domain) + 엔티티(entity) + 공통기능(common) 패키지를 루트에 위치
    * 도메인마다 각각 controller, service, repository, exception, dto 소유
    
* 서비스 계층이 API 예외를 던지는 대신 도메인 예외/범용 예외를 던지도록 수정 (서비스 계층을 웹에서 독립)

* 예외는 ControllerAdvice(ExceptionHandler) 에서 공통 처리

* 엔티티가 dto에 의존하지 않도록 변경 (dto.toEntity() 사용)

#### 1.2 세부사항

* 사용자 입력값 Bean Validation 을 사용하여 검증 

* ControllerAdvice 에서 콘솔 대신 logger 사용

* 엔티티에서 사용 안하는 양방향 연관관계와 애노테이션 제거.  

* 커맨드와 쿼리 분리

* 이미지 업로드(multipart/form-data)와 JSON 업로드(application/json) 분리 

* 이미지 업로드할 때 prefix, suffix 구분해서 suffix 만 DB에 저장

* 엔티티 매핑시 복합키 대신 유니크 제약 조건 활용

* 일대일 연관관계 주 테이블에 외래키 매핑으로 변경 (Store, StoreMetadata)

* 리스트 반환시 객체로 래핑 (추후 페이징 적용 대비)

* 쿠폰 종류 엔티티 매핑시 상속 대신 열거형 사용

* 영속성 컨텍스트 캐시를 고려해서 컨트롤러 대신 서비스에서 엔티티 생성

* 테스트를 위해 LocalDateTime을 파라미터로 분리

* 연관관계 매핑시 PK만 사용하도록 변경(UserPointLog) 참고) https://www.inflearn.com/questions/16570


### 2. TODOS

---

#### 2.1 SpringSecurity 