package com.jd.book.springboot.web;

import com.jd.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.jd.book.springboot.web.HelloController;

import java.security.Security;

// @RunWith- 테스트를 진행할때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킵니다.
// 여기서는 SpringRunner라는 스프링 실행자를 사용합니다.
// 즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 합니다.
@RunWith(SpringRunner.class)

// @WebMvcTest - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션 입니다.
// 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있습니다.
// 단, @Service, @Component, @Repository 등은 사용할 수 없습니다.
// 여기서는 컨트롤러만 사용하기 때문에 선언합니다.
@WebMvcTest(controllers = HelloController.class,
        //@WebMvcTest를 사용하는 HelloController는 @WebSecurityConfigurerAdapter, WebMvcConfigurer를 비홋한
        //@ControllerAdvice, @Controller를 읽습니다. 즉, @Repository, @Service, @Component는
        //스캔 대상이 아닙니다.. SecurityConfig는 읽었지만, SecurityConfig를 생성하기 위해 필요한
        //CustomOAuth2UserSerivce는 읽을 수가 없어 에러가 발생한 것입니다. 이를 해결하기 위해 스캔 대상에서 SecurityConfig를 제거합니다.
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {
    @Autowired // 스프링이 관리하는 Bean을 주입 받습니다.

    // 웹 API를 테스트할 때 사용합니다.
    // 스프링 MVC 테스트의 시작점입니다.
    // 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있습니다.
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void  hello가_리턴된다() throws Exception{
        String hello = "hello";

        // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 합니다.
        // 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있습니다.
        mvc.perform(get("/hello"))
                // mvc.perform의 결과를 검증합니다.
                // HTTP Header의 Status를 검증합니다. 우리가 흔히 알고 있는 200, 404, 500등의 상태를 검증합니다.
                // 여기선 Ok, 즉 200인지 아닌지를 검증합니다.
                .andExpect(status().isOk())
                // 응답 본문의 내용을 검증합니다
                // Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증합니다.
                .andExpect(content().string(hello));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        // param - API 테스트 할 때 사용될 요청 파라미터를 설정합니다.
                        // 단, 값은 String만 허용됩니다.
                        // 그래서 숫자/날짜 들의 데이터도 등록할 때는 문자열로 변경해야만 가능합니다.
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                //jsonPath - JSON 응답값을 필드별로 검증할 수 있는 메소드입니다.
                //$를 기준으로 필드명을 명시합니다.
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount))

        );
    }
}