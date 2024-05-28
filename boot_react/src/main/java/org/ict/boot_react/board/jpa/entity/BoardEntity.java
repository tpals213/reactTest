package org.ict.boot_react.board.jpa.entity;


//테이블 생성에 대한 가이드 클래스임

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.board.model.dto.BoardDto;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="BOARD")
@Entity
@SequenceGenerator(
        name = "board_seq_bnum", sequenceName = "seq_bnum", initialValue = 21, allocationSize = 1)
public class BoardEntity {
    @Id     //JPA가 객체를 관리할 때 식별할 기본키 저장
    //@GeneratedValue(strategy = GenerationType.IDENTITY) // primary key 지정
    // 사용시 무조건 default 입력되므로 주석처리할 것
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "board_seq_bnum")
    @Column(name="BOARD_NUM", nullable = false)
    private int boardNum;
    @Column(name="BOARD_WRITER", nullable = false, length = 50)
    private String boardWriter;
    @Column(name="BOARD_TITLE", nullable = false, length = 50)
    private String boardTitle;
    @Column(name="BOARD_CONTENT", nullable = false, length = 2000)
    private String boardContent;
    @Column(name="BOARD_ORIGINAL_FILENAME")
    private String boardOriginalFilename;
    @Column(name="BOARD_RENAME_FILENAME")
    private String boardRenameFilename;
    @Column(name="BOARD_READCOUNT")
    private int boardReadcount;
    @Column(name="BOARD_DATE")
    private Date boardDate;

    @PrePersist //jpa로 가기 전에 작동됨
    public void prePersist(){
        boardDate = new Date(System.currentTimeMillis());
    }

    //entity --> dto 로 변환하는 메소드 추가함
    public BoardDto toDto(){
        return BoardDto.builder()
                .boardNum(this.boardNum)
                .boardWriter(this.boardWriter)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardOriginalFilename(this.boardOriginalFilename)
                .boardRenameFilename(this.boardRenameFilename)
                .boardReadcount(this.boardReadcount)
                .boardDate(this.boardDate)
                .build();
    }

}
