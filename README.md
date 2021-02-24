# 📖 스프링 부트와 AWS로 혼자 구현하는 웹서비스 프로젝트
<p>
  <img src="https://user-images.githubusercontent.com/54519245/108794046-68f92700-75c8-11eb-8cd2-24bce9f38834.jpg" width="400">
</p>

* 이동욱(jojoldu) 지음
* [참고 선행 레포지토리](https://github.com/jojoldu/freelec-springboot2-webservice "https://github.com/jojoldu/freelec-springboot2-webservice")

<br>

# ✍️ 이 책을 통해 공부한 것들

### 1장
* 스프링 부트 설정
* Graldle 의존성 관리 및 사용법
* IntelliJ에서 Git 사용법
* marketplace 플러그인 설치법

<br>

### 2장
<p>
  <img src="https://user-images.githubusercontent.com/54519245/108797513-b1b3de80-75ce-11eb-99a1-18ffe64d722c.gif" width="400">
</p>

* __TDD(Test Driven Development)__
  * 개발을 테스트 코드를 작성하는 것으로 시작
  * 항상 실패하는 테스트를 먼저 작성하고(Red)
  * 테스트가 통과하는 프로덕션 코드를 작성하고(Green)
  * 테스트가 통과하면 프로덕션 코드를 리팩토링한다.(Refactor)
* TDD와 단위 테스트의 차이점
* 테스트 코드 작성
* JUnit 사용법
* 유동적인 개발을 가능하게 하여 편의를 제공하는 Lombok 라이브러리

<br>

### 3장
* 자바 표준 ORM인 __JPA__
  * 객체를 관계형 데이터베이스에서 관리
  * ORM은 객체과 관계간의 패러다임 불일치를 해결해주는 기술
  * 더이상 SQL에 종속적인 개발을 하지 않아도 됨
* __Spring Data JPA__
  * 구현체 교체의 용이성 
  * 프로젝트에 적용
  * 테스트 코드 작성

<p>
  <img src="https://user-images.githubusercontent.com/54519245/108799971-30ac1580-75d5-11eb-93fa-310505a6d1c5.png" width="400">
</p>

* CRUD API 작성 및 스프링 부트 프로젝트 구조
  * Request 데이터를 받을 __Dto__
  * API 요청을 받을 __Controller__
  * 트랜잭션, 도메인 기능 간의 순서를 보장하는 __Service__
     * __Service가 아닌 Domain 레이어에서 비즈니스 로직을 처리해야한다.__
* Entity 클래스는 DB와 맞닿은 핵심 클래스
  * 계층간의 데이터 교환이 필요할 땐 Dto를 사용해아함
* JPA의 __영속성 컨텍스트__
  * 더티 체킹(Dirty Checking)
* JPA Auditing
