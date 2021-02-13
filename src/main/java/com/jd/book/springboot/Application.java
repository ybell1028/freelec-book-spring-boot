package com.jd.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing이 삭제됨
// @SpringBootApplication - 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정됩니다.
// 특히나 @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트의 최상단에 위치해야만 합니다.
@SpringBootApplication
// Application 클래스는 앞으로 만들 프로젝트의 메인 클래스가 됩니다.
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run으로 인해 내장 WAS를 실행합니다.
        // 이렇게 되면 항상 서버에 톰캣을 설치할 필요가 없게 되고, 스프링 부트로 만들어진 Jar파일로 실행하면 됩니다.
        SpringApplication.run(Application.class, args);
    }
}
