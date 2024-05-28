package org.ict.boot_react.notice.jpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.ict.boot_react.common.SearchDate;
import org.ict.boot_react.notice.jpa.entitiy.NoticeEntity;
import org.ict.boot_react.notice.jpa.entitiy.QNoticeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepository {
    // 방법 3 : 상속, 재구현(impement) 없는 querydsl 만으로 구성하는 리포지토리 클래스

    // Querydsl 에 대한 config 클래스를 먼저 만들고 나서 필드 선언
    private final JPAQueryFactory queryFactory; // 이것만 선언하면 됨 반드시 final
    private final EntityManager entityManager;  // JPQL 사용을 위해 의존성 주입
    private QNoticeEntity notice = QNoticeEntity.noticeEntity;

    // 조회수 1증가 처리 => jpa 제공 메소드로 save() 메소드 사용해도 됨
    public void updateReadCount(int noticeNo){
        queryFactory
                .update(notice)
                .set(notice.readCount, notice.readCount.add(1))
                .where(notice.noticeNo.eq(noticeNo))
                .execute();
        entityManager.flush();
        entityManager.clear();
    }

    // 가장 최근 공지글 1개 조회
    public NoticeEntity findLast(){
        return queryFactory
                .selectFrom(notice) // select * from notice
                .orderBy(notice.noticeNo.desc())    // order by notice_no desc
                .fetchOne();    // 내림차순 정렬 공지글의 첫번째
        // .fetch().get(0);

    }

    // 최근 등록글 3개 조회 : querydsl 에서 jpql 사용하는 경우
    public List<NoticeEntity> findNewTop3(){
        // 주의 : querydsl 에서는 select 절과 where 절에서의 서브쿼리는 지원하지만,
        //          from 절에서의 서브쿼리는 지원하지 않는다. => 쿼리를 나눠서 실행 또는 조인 등으로 해결
        // JPQL 도 where 절과 group by 절에서만 서브쿼리 사용 가능함 => from 절에서 서브쿼리 사용 못함

        return entityManager.createNativeQuery(
                        "select NOTICENO, NOTICETITLE, NOTICEDATE from NOTICE order by noticeno desc")
                .getResultList();
    }

    // 검색 페이징 처리를 위한 검색 카운트 관련 메소드 ----------------------
    public long countByTitle(String keyword){
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)
                .where(notice.noticeTitle.like("%" + keyword + "%"))
                .fetchCount();
    }

    public long countByContent(String keyword){
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)
                .where(notice.noticeContent.like("%" + keyword + "%"))
                .fetchCount();
    }

    public long countBySearchDate(SearchDate searchDate){
        //querydsl 사용
        return queryFactory
                .selectFrom(notice)
                .where(notice.noticeDate.between(searchDate.getBegin(), searchDate.getEnd()))
                .fetchCount();
    }

    // 검색 관련 메소드
    public List<NoticeEntity> findBySearchTitle(String keyword, Pageable pageable){
        return queryFactory
                .selectFrom(notice)
                .where(notice.noticeTitle.like("%" + keyword + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<NoticeEntity> findBySearchContent(String keyword, Pageable pageable){
        return queryFactory
                .selectFrom(notice)
                .where(notice.noticeContent.like("%" + keyword + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<NoticeEntity> findBySearchDate(SearchDate searchDate, Pageable pageable){
        return queryFactory
                .selectFrom(notice)
                .where(notice.noticeDate.between(searchDate.getBegin(), searchDate.getEnd()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


}
