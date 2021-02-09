package com.jd.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//보통 MyBatis 등에서 Dao라고 불리는 DB Layer 접근자입니다.
//JPA에선 Repository라고 부르며 인터페이스로 생성합니다. 단순히 인터페이스를 생성 후
//JpaRepository<Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드가 자동으로 생성됩니다.

//**여기서 주의해야 할 점은 Entity 클래스와 기본 Entity Repository는 함께 위치해야하는 점**입니다.
//둘은 아주 밀접한 관계이고, Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수가 없습니다.

//나중에 프로젝트 규모가 커져 도메인 별로 프로젝트를 분리해야 한다면 이때 Entity 클래스와
//기본 Repository는 함께움직여야하므로 도메인 패키지에서 함께 관리합니다.
public interface PostsRepository extends JpaRepository<Posts, Long> {

    //SpringDataJpa에서 제공하지 않는 메소드는 이처럼 쿼리로 작성해도 됩니다.
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
