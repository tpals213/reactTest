package org.ict.boot_react.member.jpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.ict.boot_react.common.SearchDate;
import org.ict.boot_react.member.jpa.entity.MemberEntity;
import org.ict.boot_react.member.jpa.entity.QMemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;  //JPQL 사용을 위해 DI 추가함

    private QMemberEntity member = QMemberEntity.memberEntity;

    @Override
    public MemberEntity findByUserId(String userid) {
        //queryDSL 사용
        return queryFactory
                .selectFrom(member)
                .where(member.userId.eq(userid))
                .fetchOne();  //한 건 조회
    }

    @Override
    public int updateLoginOK(String userId, String loginOk) {
        //JPQL 사용
        return entityManager.createNativeQuery("update member set login_ok = :loginOk where userid = :userId")
                .setParameter("loginOk", loginOk)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public long searchIDCount(String keyword) {
        //queryDSL 사용
        return queryFactory
                .selectFrom(member)
                .where(member.userId.like("%" + keyword + "%"))
                .fetchCount();
    }

    @Override
    public long searchGenderCount(String keyword) {
        //queryDSL 사용
        return queryFactory
                .selectFrom(member)
                .where(member.gender.eq(keyword))
                .fetchCount();
    }

    @Override
    public long searchAgeCount(int age) {
        return queryFactory
                .selectFrom(member)
                .where(member.age.between(age, age + 9))
                .fetchCount();
    }

    @Override
    public long searchDateCount(SearchDate searchDate) {
        return queryFactory
                .selectFrom(member)
                .where(member.enrollDate.between(searchDate.getBegin(), searchDate.getEnd()))
                .fetchCount();
    }

    @Override
    public long searchLoginOKCount(String keyword) {
        return queryFactory
                .selectFrom(member)
                .where(member.loginOk.eq(keyword))
                .fetchCount();
    }

    @Override
    public List<MemberEntity> findBySearchUserid(String keyword, Pageable pageable) {
        //querydsl 사용
        return queryFactory
                .selectFrom(member)
                .where(member.userId.like( "%" + keyword + "%" ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<MemberEntity> findBySearchGender(String keyword, Pageable pageable) {
        //querydsl 사용
        return queryFactory
                .selectFrom(member)
                .where(member.gender.eq(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<MemberEntity> findBySearchAge(int age, Pageable pageable) {
        //querydsl 사용
        return queryFactory
                .selectFrom(member)
                .where(member.age.between(age, age + 9))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<MemberEntity> findBySearchEnrollDate(SearchDate searchDate, Pageable pageable) {
        //querydsl 사용
        return queryFactory
                .selectFrom(member)
                .where(member.enrollDate.between(searchDate.getBegin(), searchDate.getEnd()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<MemberEntity> findBySearchLoginOK(String keyword, Pageable pageable) {
        //querydsl 사용
        return queryFactory
                .selectFrom(member)
                .where(member.loginOk.eq(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
