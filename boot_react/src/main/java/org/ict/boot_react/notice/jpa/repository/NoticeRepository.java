package org.ict.boot_react.notice.jpa.repository;


import org.ict.boot_react.notice.jpa.entitiy.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {
    // jpa 각 제공하는 기본 메소드를 사용하려면 필요함
}
