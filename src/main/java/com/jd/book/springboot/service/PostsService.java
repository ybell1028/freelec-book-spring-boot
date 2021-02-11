package com.jd.book.springboot.service;

import com.jd.book.springboot.domain.posts.Posts;
import com.jd.book.springboot.domain.posts.PostsRepository;
import com.jd.book.springboot.web.dto.PostsListResponseDto;
import com.jd.book.springboot.web.dto.PostsResponseDto;
import com.jd.book.springboot.web.dto.PostsSaveRequestDto;
import com.jd.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    //스프링에서 Bean을 주입받는 방식들은 다음과 같습니다.
    //@Autowired, setter, 생성자
    //이 중 가장 권장하는 방식이 생성자로 주입받는 방식입니다.(@Autowired는 권장하지 않습니다.)

    //그러면 앞에서 생성자는 어디에 있을까요? 바로 @RequiredArgsConstructor가 해결해줍니다.
    //final이 선언된 모든 필드를 인자값으로 하는 생성자를 롬복의 @RequiredArgConstructor가 대신 생성해 준 것 입니다.

    //롬복 어노테이션을 사용한 이유는 해당 클래스의 의존성 관계가 변경 될 때마다
    //생성자 코드를 계속해서 수정하는 번거로움을 해결하기 위함입니다.

    @Transactional // 데이터베이스 트랜잭션 설정 어노테이션
    //DB의 접근이 하나라도 실패시 rollback
    //비지니스 로직과 트랜잭션 관리는 모두 Service에 하기때문에 Service 메소드는 @Transactional를 사용
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId(); // insert
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id) // id를 가진 게시물을 찾고
                .orElseThrow(() -> // 없다면 Throw
                        new IllegalArgumentException("해당 게시글이 없습니다. id=" + id ));
        posts.update(requestDto.getTitle(), requestDto.getContent()); // posts.update()메소드로 업데이트
        return id;

        //update 기능에서 **DB에 쿼리를 날리는 부분이 없습니다.**
        //이게 가능한 이유는 **JPA의 영속성 컨텍스트 때문입니다.**
        //영속성 컨텍스트란, 엔티티를 영구 저장하는 환경입니다. 일종의 논리적 개념이라고 보시면 되며
        //JPA의 핵심 내용은 **엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈립니다.**

        //JPA의 엔티티 매니저가 활성화된 상태로(Spring Data Jpa를 쓴다면 기본 옵션)
        //**트랜잭션 안에서 DB에서 데이터를 가져오면** 이 데이터는 영속성 컨텍스트가 유지된 상태입니다.
        //이 상태에서 해당 데이터의 값을 변경하면 **트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영**합니다.
        //즉, Entity 객체의 값만 변경하면 별도로 **Update 쿼리를 날릴 필요가 없다는 것**이죠.
        //이 개념을 **더티 체킹**이라고 합니다.
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> // 없다면 Throw
                        new IllegalArgumentException("해당 게시글이 없습니다. id=" + id ));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    //(readOnly = true)를 주면 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 속도가 개선되기 때문에
    //등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드에서 사용하는 것을 추천합니다.
    public List<PostsListResponseDto> findAllDesc(){
        //람다식 설명
        //.map(PostsListResponseDto::new)는 .map(posts -> new PostsListResponseDto(posts))와 같습니다
        //postRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 반환하는 메소드입니다.
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
        //추가 조사 - Java8의 Stream이란?
        //Stream은 배열, List등의 요소들의 처리를 담당하느 ㄴ클래스로써 기존의 배열처리를 간단하게 해주며,
        //function style로 처리할 수 있도록 해줍니다. 그리고 병렬처리 Optional등 다양한 처리를 할 수 있도록 지원해줍니다.

        //collection() 메서드는 Stream을 다시 List, Map 등 다양한 우리가 일반적으로 알고 있는 Collection, Object로 변환해 줍니다.
        //Collectors란 "Stream을 일반적인 List, Set등으로 변경시키는 Stream 메서드"라고 입니다.
        //toList collector는 모든 Stream elements를 List나 Set instance로 변경하는 메서드입니다.
        //중요한 것은 특정한 Collection으로 변환이 되는것은 아니라는 것입니다.
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id ));

        postsRepository.delete(posts);
    }
}
