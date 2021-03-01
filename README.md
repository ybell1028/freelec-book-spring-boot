# 📖 스프링 부트와 AWS로 혼자 구현하는 웹서비스 프로젝트
<p>
  <img src="https://user-images.githubusercontent.com/54519245/108794046-68f92700-75c8-11eb-8cd2-24bce9f38834.jpg" width="400">
</p>

* 이동욱(jojoldu) 지음
* [참고 선행 레포지토리](https://github.com/jojoldu/freelec-springboot2-webservice "https://github.com/jojoldu/freelec-springboot2-webservice")

<br>

# ✍️ 이 책을 통해 공부한 것들

## 1장
* 스프링 부트 설정
* Graldle 의존성 관리 및 사용법
* IntelliJ에서 Git 사용법
* marketplace 플러그인 설치법

<br>

## 2장
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

## 3장
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

<br>

## 4장
* __템플릿 엔진__ 이란?
  * 지정된 템플릿 양식과 데이터가 합쳐져 HTML 문서를 출력하는 소프트웨어
  * JSP, Freemarker 등은 서버 사이드 템플릿 엔진
  * React, Vue.js, Angular 등은 클라이언트 사이드 템플릿 엔진
* __서버 사이드 템플릿 엔진의 요소는 JS 안에서 사용할 수 없다.__ (서로 작동하는 위치가 각각 서버와 클라이언트로 다르기 때문에)
* 수많은 언어를 지원하는 가장 심플한 템플릿 엔진 - __머스테치(Mustache)__
  * 문법이 다른 템플릿 엔진보다 심플함
  * 로직 코드를 사용할 수 없어 View의 역할과 서버의 역활이 명확하게 분리됨
  * .js와 .java 확장자를 모두 지원해, 하나의 문법으로 클라이언트/서버 템플릿을 모두 사용가능함
  * IntelliJ Community 버전에서도 플러그인 지원
* Controller에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정되며 View Resolver가 처리하게 됨
  * ex) `return "index"; -> return (src/main/resources/templates/)"index"(.mustache);`
* __TestRestTemplate__
  * SpringBootTest에서 Web Environment 설정을 하였다면 TestRestTemplate은 그에 맞춰서 자동으로 설정되어 Bean이 생성됨 
  * MockMvc는 Servlet Container를 사용하지 않고 SpringBootTest와 TestRestTemplate는 사용하지 않는다.
  * 그래서 마치 실제 서버가 동작하는 것처럼 테스트를 수행할 수 있다.
* Bootstrap을 layout 방식으로 적용
* JS로 Button 기능 구현
  * __scope의 중요성__
* 규모가 있는 프로젝트의 데이터 조회는 복잡한 조건 등으로 인해 Entity 클래스만으로 처리하기 어려워 __조회용 프레임워크를 추가로 사용함__
  * 책에서는 __Querydsl__ 을 추천함
  * 타입 안정성이 보장, 국내에서 많은 회사들이 사용, 레퍼런스가 많다는 장점이 있음
* 람다식, stream()에 대해서도 나중에 자세히 알아보자
* CRUD 기능 구현
  * Controller에 API 라우팅 구현
  * mustache를 사용한 View 페이지 구현
  * JS를 사용해 API에 요청 기능

<br>

## 5장
* __스프링 시큐리티(Spring Security)__
  * 막강한 인증(Authtentication)과 인가(Authorization, 혹은 권한 부여) 기능을 가진 프레임워크로 사실상 스프링 기반 애플리케이션 보안의 표준
* 소셜 로그인 서비스 __Oauth2__ 를 사용하는 이유와 편리함
* __Google 로그인__ 연동
* 세션 저장을 위해 별도의 Dto를 사용하는 이유
  > 엔티티 클래스는 언제 다른 엔티티와 관계가 형성될지 모릅니다.  
  > 예를 들어 @OneToMany, @ManyToOne 등 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되니 __성능 이슈, 부수 효과__ 가 발생할 확률이 높습니다.  
  > 그래서 __직렬화 기능을 가진 세션 Dto__ 를 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움이 됩니다.
* __Annotation 기반으로 코드 개선하는 법__
* 세션 저장소로 데이터베이스 사용하기
  * 실제 현업에서 사용하는 세션 저장소 3가지
    > 1. 톰캣 세션  
    > 2. __MySQL과 같은 데이터베이스__ (프로젝트에서 사용)
    > 3. Redis, Memcached와 같은 메모리 DB
  * spring-session-jdbc 등록
  * applicatio.properties에 세션 저장소를 jdbc로 선택하도록 코드 추가
* __Naver 로그인__ 연동
  * 스프링 시큐리티를 공식 지원하지 않기 때문에 Common-OAuth2Provider에서 설정해주던 값들도 전부 수동 입력
  * OAuthAttribute에 네이버인지 판단하는 코드와 생성자 추가
* 기존 테스트에 시큐리티 적용하기
