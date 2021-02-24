package com.jd.book.springboot.web;


import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

//ProfileController는 특별히 스프링 환경이 필요하지는 않습니다.
//그래서 @SpringBootTest 없이 테스트 코드를 작성합니다.
public class ProfileControllerUnitTest {
    @Test
    public void real_profile이_조회된다(){
        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void real_profile이_없으면_첫_번째가_조회된다(){
        //given
        String expectedProfile = "oauth";

        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void active_profile이_없으면_default가_조회된다(){
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
    
    /* Environment는 인터페이스라 가짜 구현체인 MockEnvironment(스프링에서 제공)를 사용해서 테스트하면 됩니다.
    * 이렇게 해보면 생성자 DI가 얼마나 유용한지 알 수 있습니다.
    * 만약 Environment를 @Autowired로 DI 받았다면 __이런 테스트 코드를 작성하지 못했습니다__
    * 항상 스프링 테스트를 해야만 했겠죠? 그리고 이 /profile이 인증 없이도 호출될 수 있게 SecurityConfig 클래스에 제외코드를 추가합니다.*/
}
