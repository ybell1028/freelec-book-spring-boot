package com.jd.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd.book.springboot.domain.posts.Posts;
import com.jd.book.springboot.domain.posts.PostsRepository;
import com.jd.book.springboot.web.dto.PostsSaveRequestDto;
import com.jd.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//HelloController와 달리 @WebMvcTest를 사용하지 않았습니다.
//@WebMvcTest의 경우 JPA 기능이 작동하지 않기 때문인데, Controller와 ControllerAdvice등
//외부 연동과 관련된 부분만 활성화되니 지금같이 JPA 기능까지 한번에 테스트 할 때는
//@SpringBootTest와 TestRestTemplate를 사용하면 됩니다.
public class PostApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach //매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성합니다.
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    //@WithMockUser(roles="USER") - 인증된 모의(가짜) 사용자를 만들어서 사용합니다.
    //roles에 권한을 추가할 수 있습니다.
    //즉, 이 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자가 API를 요청하는 것과 동인한 효과를 가지게 됩니다.

    //이정도만 하면 테스트가 될 것 같지만, 실제로 작동하진 않습니다.
    //@WithMockUser가 MockMvc에서만 작동하기 때문입니다. 현재 PostsApiControllerTest는
    //@SpringBootTest로만 되어있으며 MockMvc를 전혀 사용하지 않습니다.
    @WithMockUser(roles="USER")
    public void Posts_등록된다() throws Exception{
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "api/v1/posts";

        //when - url로 만들어둔 requestDto를 보낸다.

        //mvc.perform - 생성된 MockMvc를 통해 API를 테스트합니다.
        //본문(Body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환합니다.
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정된다() throws Exception{
        //given
        Posts savedPosts = postsRepository.save(
                Posts.builder() // Post 엔티티를 builder로 생성해서
                .title("title") //.save(insert)
                .content("content")
                .author("author")
                .build()
        );
        
        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();
        
        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        
        //when - restTemplate를 이용해서 update를 요청, 그리고 응답값은 responseEntity에 담긴다.
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        
        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
