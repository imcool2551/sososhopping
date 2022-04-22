# sososhop-api

---

sososhop 애플리케이션의 api서버입니다. 
프로토타입에서 V1 로 리팩토링중입니다.


### Refactor List

---

* 패키지 구조 수정 (도메인+엔티티+공통기능 패키지를 최상위, 도메인 패키지밑에 controller, service, repository)
* 사용자 입력값 검증 추가 with Bean Validation 
* 서비스 계층이 API 예외를 던지지 않고 도메인에 특화된 예외를 던지도록 수정
* 도메인 특화된 예외는 `@ResponseStatus` 애노테이션을 통해 공통으로 처리
* ControllerAdvice 에서 e.printStackTrace() 대신 logger 사용
