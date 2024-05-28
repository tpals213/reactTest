package org.ict.boot_react.board.jpa.repository;


import org.ict.boot_react.board.jpa.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepoository extends JpaRepository<ReplyEntity, Integer> {
}
