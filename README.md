### 1. SosoShopping Api Server v1.0

---

- 소상공인과 소비자 각각의 앱을 위한 API 서버입니다.
- 소상공인은 자신의 점포에 대한 각종 정보들을 앱을 통해 등록할 수 있습니다.
- 소비자는 등록된 정보들을 다양한 방법으로 얻을 수 있습니다.

### 2. Package structure

---

- main
  - java.com.sososhopping
    - common: 프로젝트 전반에 걸쳐 사용되는 공용 클래스
      - config: 설정 클래스
      - dto: 공용 DTO 클래스
      - exception: 공용 예외 클래스
      - security: Spring Security 관련 클래스
      - service: 공용 서비스 클래스
    - domain: 도메인으로 분류된 패키지. 도메인마다 하위 패키지로 dto, exception, controller, service, repository 위치
      - admin: 관리자 도메인. ex) 점포 등록, 유저 신고 등
      - auth: 인증 도메인. ex) 로그인, 회원 가입 등
      - coupon: 쿠폰 도메인. ex) 쿠폰 발급, 쿠폰 사용 등
      - orders: 주문 도메인. ex) 주문하기, 주문처리 등
      - owner: 점주 도메인. ex) 점포 등록, 점포 정보 변경 등
      - point: 포인트 도메인. ex) 포인트 제도 설정, 포인트 적립/사용 등
      - report: 신고 도메인. ex) 유저 신고, 점주 신고 등
      - store: 점포 도메인. ex) 점포 조회, 상품 등록, 리뷰 등록 등
      - user: 사용자 도메인. ex) 사용자 정보 조회, 사용자 정보 변경 등
    - entity: JPA로 매핑한 엔티티 클래스. 하위 패키지는 도메인과 동일. 비즈니스 로직 존재 (Domain Model Pattern).
  - resources
    - http: 도메인 별로 엔드 포인트에대한 HTTP Request 파일 위치
    - sql: MySQL DDL 파일 위치
    - static: js/css/img 정적 파일 위치
    - templates: 관리자 페이지에 사용되는 Thymeleaf 템플릿 엔진 파일 위치
- test.com.sososhopping
  - domain: 도메인에대한 테스트 파일 위치
  - entity: 엔티티에대한 테스트 파일 위치

### 3. Refactoring points compared to prototype

---

#### 3.1. Architecture

- 패키지 구조 수정
  - 도메인(domain) + 엔티티(entity) + 공통기능(common) 패키지를 루트에 위치
    - 도메인마다 각각 controller, service, repository, exception, dto 소유
- 서비스 계층이 API 예외를 던지는 대신 도메인 예외/범용 예외를 던지도록 수정 (서비스 계층을 웹에서 독립)

- 예외는 ControllerAdvice(ExceptionHandler) 에서 공통 처리

- 엔티티가 dto에 의존하지 않도록 변경 (Pure Entity)

- 서비스 레이어 로직 도메인 객체로 이동 (Transactional Script Pattern to Domain Model Pattern)

#### 3.2. Minor details

- 모든 사용자 입력값 Bean Validation 을 사용하여 검증

- ControllerAdvice 에서 콘솔 대신 logger 사용

- 양방향 연관관계 최소화

- 커맨드와 쿼리 분리

- 이미지 업로드(multipart/form-data)와 JSON 업로드(application/json) 분리

- 이미지 업로드할 때 prefix, suffix 구분해서 suffix 만 DB에 저장

- 엔티티 매핑시 복합키 대신 유니크 제약 조건 활용. (모든 테이블에 대리키 사용)

- 일대일 연관관계 주 테이블에 외래키 매핑으로 변경 (Store, StoreMetadata)

- 리스트 반환시 객체로 래핑 (추후 페이징 적용 대비)

- 쿠폰 종류 엔티티 매핑시 상속 대신 열거형 사용

- 영속성 컨텍스트 캐시를 고려해서 컨트롤러 대신 서비스에서 엔티티 생성

- 테스트를 위해 LocalDateTime을 파라미터로 분리

- 연관관계 매핑시 PK만 사용하도록 변경(UserPointLog) 참고) https://www.inflearn.com/questions/16570

- JPA Embedded Type 이용해서 클래스 분리 (OOP)

#### 3.3. TODOS

- SpringSecurity

### 4. How it looks

---

#### 4.1 고객 앱

- 회원가입

  ![](https://velog.velcdn.com/images/imcool2551/post/9c50fa66-a793-4f00-9f27-97fe8588d4bb/image.gif)

- 로그인

  ![](https://velog.velcdn.com/images/imcool2551/post/3f7079d9-3bf9-42f0-9369-895c76fb40f7/image.gif)

- 점포 검색 by 상점 이름

  ![](https://velog.velcdn.com/images/imcool2551/post/5c068d2b-153c-476c-9faf-c7772570fea4/image.gif)

- 점포 검색 by 판매 물품

  ![](https://velog.velcdn.com/images/imcool2551/post/a54d338c-6944-4b2e-a27b-4293d70a5eef/image.gif)

- 점포 점색 by 점포 종류

  ![](https://velog.velcdn.com/images/imcool2551/post/dab5f03c-ff99-4721-b884-642594fa5f34/image.gif)

- 관심점포

  ![](https://velog.velcdn.com/images/imcool2551/post/cb8a41d0-fc75-45ea-af61-f735d8b863cd/image.gif)

- 점포 정보 검색

  ![](https://velog.velcdn.com/images/imcool2551/post/2fdfd0c3-edea-42a9-bb20-ac146768b004/image.gif)

- 점포 신고하기

  ![](https://velog.velcdn.com/images/imcool2551/post/175e2873-d77e-4815-a439-a075144b3ae2/image.gif)

- 장바구니

  ![](https://velog.velcdn.com/images/imcool2551/post/a2ea79ea-d2b4-4f39-817c-fe8693df0449/image.gif)

- 쿠폰 발급

  ![](https://velog.velcdn.com/images/imcool2551/post/a550d041-ef01-4c21-bc9e-92863b2ce446/image.gif)

- 포인트 조회

  ![](https://velog.velcdn.com/images/imcool2551/post/54598d76-977c-4893-9d18-a87ed3e9abfa/image.gif)

- 점포 후기 관리

  ![](https://velog.velcdn.com/images/imcool2551/post/f9ceaa93-196d-4751-bbb3-9af2dea2b6bb/image.gif)

- 주문하기

  ![](https://velog.velcdn.com/images/imcool2551/post/58654f8f-017d-4c2c-a4fe-2c071c6345cc/image.gif)

  ![](https://velog.velcdn.com/images/imcool2551/post/1ea5a655-fc5c-4e6e-822a-970f0b81e5c2/image.gif)

  ![](https://velog.velcdn.com/images/imcool2551/post/ed531254-6714-4f79-a9ae-ae5cf4df16cc/image.gif)

- 주문 내역 조회

  ![](https://velog.velcdn.com/images/imcool2551/post/1f0a317f-3bbd-47aa-9c98-c8a7dc2cc34f/image.gif)

#### 4.2 점주 앱

- 회원 가입

  ![](https://velog.velcdn.com/images/imcool2551/post/16705d0a-2d74-49bf-a183-9b73f2c4e4d1/image.gif)

- 로그인

  ![](https://velog.velcdn.com/images/imcool2551/post/2456a803-9df4-41d6-bdab-b2d84d7bf710/image.gif)

- 점포 등록

  ![](https://velog.velcdn.com/images/imcool2551/post/afe9612c-2486-415a-b5e2-d404c4b32955/image.gif)

  ![](https://velog.velcdn.com/images/imcool2551/post/ac37ab1a-3d56-438b-be4e-ddc2e59f74f7/image.gif)

- 내 점포 목록

  ![](https://velog.velcdn.com/images/imcool2551/post/0f79cb22-41a6-407e-8b07-520a89095374/image.gif)

- 고객 포인트 관리

  ![](https://velog.velcdn.com/images/imcool2551/post/107010d3-d880-4019-a1eb-8af270a5aa11/image.gif)

- 쿠폰 발급

  ![](https://velog.velcdn.com/images/imcool2551/post/679aab50-a603-45d5-b88b-d81d0bde784e/image.gif)

- 고객 쿠폰 사용

  ![](https://velog.velcdn.com/images/imcool2551/post/eed8ba0f-8975-415e-8b6c-722afa81bce0/image.gif)

- 물품 등록

  ![](https://velog.velcdn.com/images/imcool2551/post/7ad709ba-a085-44ea-a1e3-d16d004d75a3/image.gif)

- 물품 수정

  ![](https://velog.velcdn.com/images/imcool2551/post/d0b9f573-3bf6-4762-984b-478d28440172/image.gif)

- 게시글 등록 및 수정

  ![](https://velog.velcdn.com/images/imcool2551/post/c48bc9d0-d0c9-4ede-962b-2949ebf30514/image.gif)

- 주문 처리

  ![](https://velog.velcdn.com/images/imcool2551/post/e79dcd95-2c14-4865-93f0-ebf642516731/image.gif)

  ![](https://velog.velcdn.com/images/imcool2551/post/b6e58e2c-559a-4274-978a-aa820e1bce52/image.gif)

- 주문 일정 조회

  ![](https://velog.velcdn.com/images/imcool2551/post/0ff39257-b7e9-42fc-b830-0f63c9301621/image.gif)

- 점포 정보 수정

  ![](https://velog.velcdn.com/images/imcool2551/post/7b246f40-4abd-4e32-a592-5ecd82f43c1e/image.gif)

- 가계부 관리

  ![](https://velog.velcdn.com/images/imcool2551/post/e4f19dd3-0266-4222-99b8-a7dd8a9e88f8/image.gif)
