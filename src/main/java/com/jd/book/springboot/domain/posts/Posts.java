package com.jd.book.springboot.domain.posts;

//도메인이란 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제영역이라고 생각하면 됩니다.
//기존 MyBatis와 같은 쿼리 매퍼를 사용했다면 dao 패키지를 떠올리겠지만, 그것과는 조금 결이 다르다고 생각하면 됩니다.
//그간 xml에 쿼리를 담고, 클래스는 오로지 쿼리의 결과만 담던 일들이 모두 도메인 클래스라고 불리는 곳에서 해결됩니다.

//이동욱님은 주요 어노테이션을 클래스에 가깝게 두는데, 이렇게 하면 코틀린 등의 새 언어 전환으로
//선택적인 어노테이션이 더이상 필요 없을 경우 쉽게 삭제할 수 있는 이점 때문이다.

import com.jd.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // lombok 어노테이션 - 기본 생성자 (public Posts() {}) 자동 추가

//@Entity - 테이블과 링크될 클래스임을 나타냅니다
//기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭합니다.
//SalesManager.java -> sales_manager table
@Entity
//여기서 Post 클래스는 실제 DB의 테이블과 매칭될 클래스이며 보통 Entity 클래스라고도 합니다.
//JPA를 사용하시면 DB 데이터에 작업할 경우 실제 쿼리를 날리기보다는, 이 Entity 클래스의 수정을 통해 작업합니다.
public class Posts extends BaseTimeEntity {
    @Id // 해당 테이블의 PK(Primary Key) 필드를 나타냅니다
    //@GeneratedValue - PK의 생성규칙을 나타냅니다.
    //@스프링부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 됩니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //참고! - 웬만하면 Entity의 PK는 Long 타입의 Auto_increment로 둘 것
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    //@Column - 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 됩니다.
    //사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용합니다.
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // lombok 어노테이션 - 해당 클래스의 빌더 패턴 클래스를 생성
    // 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //서비스 초기 구축 단계에선 테이블 설계(여기선 Entity 설계)가 빈번하게 변경되는데
    //이때 롬복의 어노테이션들은 코드 변경량을 최소화시켜 주기 때문에 적극적으로 사용합니다.

    //자바 빈 규약을 생각하면서 getter/setter를 무작정 생성하는 경우가 있는데
    //이렇게 되면 해당 클래스의 인스턴스 값들이 언제 어디서 변해야하는지
    //코드상으로 명확하게 구분할 수가 없어, 차후 기능 변경시 정말 복잡해집니다.

    //그래서 **Entity 클래스에서는 절대 Setter 메소드를 만들지 않습니다**
    //대신 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야만 합니다.

    //값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경하는 것을 전제로 합니다.
    //이 책에서는 생성자 대신에 @Builder를 통해 제공되는 빌더 클래스를 사용합니다.
    //생성자나 빌더나 생성 시점에서 값을 채워주는 역할은 똑같습니다.
    // 빌더를 사용하게 되면 어느 필드에 어떤 값을 채워야 할지 명확하게 인지할 수 있습니다.

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
