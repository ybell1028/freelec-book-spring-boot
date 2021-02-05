package com.jd.book.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void 롬복_기능_테스트(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //assertThat은 assertj라는 테스트 컴증 라이브러리의 검증 메소드입니다.
        //검증하고 싶은 대상은 메소드 인자로 받습니다.
        //메소드 체이닝이 지원되어 isEqualTo와 같이 메소드를 이어서 사용할 수 있습니다.

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
        //isEqualTo는 동등 비교 메소드입니다.
        //assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공입니다.

        //여기서 Junit의 기본 assertThat이 아닌 assertj의 assertThat을 사용했습니다.
        //Junit과 비교하여 assertj의 장점은
        //1. CoreMatchers와 달리 추가적으로 라이브러리가 필요하지 않습니다.
        //2. 자동완성이 좀 더 확실하게 지원됩니다.

        // 정상적으로 기능이 수행되는 것을 확인했습니다!
        // @Getter로 get 메소드가, @RequiredArgsConstructor로 생성자가 자동으로 생성되는 것이 증명되었습니다.
    }
}
