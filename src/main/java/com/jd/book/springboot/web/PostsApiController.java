package com.jd.book.springboot.web;

import com.jd.book.springboot.service.PostsService;
import com.jd.book.springboot.web.dto.PostsResponseDto;
import com.jd.book.springboot.web.dto.PostsSaveRequestDto;
import com.jd.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // final, @NonNull 인 필드값만 파라미터로 받는 생성자를 생성
@RestController
public class PostsApiController { // 스프링에서 Controller는 노드의 Route같은 역할을 해준다.
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto); //
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }
}
