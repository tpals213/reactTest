package org.ict.boot_react.board.jpa.repository;

import org.ict.boot_react.board.jpa.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

//MyBatis 의 SqlSession 과 같은 역할을 수행함, 마이바티스의 Mapper 인터페이스와 같음
//JPA 의 Repository는 JpaRepository
//제네릭스는 <엔티티클래스명, @id프로퍼티의 클래스자료형> 표기함


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    //JPQL 을 이용한 Repository
    
    //JPA는 서브쿼리 사용 안됨
    //@Query + Native Query 사용 (테이블명과 컬럼명 사용)
    @Query(value = "select board_num, board_title, board_readcount from board order by board_readcount desc", nativeQuery = true)
    List<BoardNativeVo> findTop3();
    //nativeQuery 사용시 컬럼명과 같은 get메소드로만 구성된 nativeVo 인터페이스가 필요함

    //@Query + JPQL 사용 (엔티티와 프로퍼티 사용)
    @Query(value="SELECT b FROM BoardEntity b WHERE b.boardTitle LIKE %:keyword%", countQuery = "SELECT COUNT(b) FROM BoardEntity b WHERE b.boardTitle LIKE %:keyword%")
    Page<BoardEntity> findSearchTitle(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

    @Query(value="SELECT b FROM BoardEntity b WHERE b.boardWriter LIKE %:keyword%",countQuery = "SELECT COUNT(b) FROM BoardEntity b WHERE b.boardWriter LIKE %:keyword%")
    Page<BoardEntity> findSearchWriter(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

    @Query(value="SELECT b FROM BoardEntity b WHERE b.boardDate BETWEEN :begin AND :end",countQuery = "SELECT COUNT(b) FROM BoardEntity b WHERE b.boardDate BETWEEN :begin AND :end")
    Page<BoardEntity> findSearchDate(@Param("begin") Date begin,@Param("end") Date end, @Param("pageable") Pageable pageable);


    //@Query + Native Query 사용 ( 테이블명과 컬럼명 사용) 페이징처리 때문에 숫자 세어옴
    @Query(value="select count(*) from board b where b.board_title like %:keyword%", nativeQuery = true)
    Long countSearchTitle(@Param("keyword") String keyword);

    @Query(value="select count(*) from board b where b.board_writer like %:keyword%", nativeQuery = true)
    Long countSearchWriter(@Param("keyword") String keyword);

    @Query(value="select count(*) from board b where between :begin and :end", nativeQuery = true)
    Long countSearchTitle(@Param("begin") Date begin, @Param("end") Date end);

    @Query(value="select max(board_num) from board b", nativeQuery = true)
    int findLastBoardNum();


}
