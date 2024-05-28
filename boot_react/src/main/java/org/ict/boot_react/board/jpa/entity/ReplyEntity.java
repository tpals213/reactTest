package org.ict.boot_react.board.jpa.entity;


//테이블 생성에 대한 가이드 클래스임

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.board.model.dto.BoardDto;
import org.ict.boot_react.board.model.dto.ReplyDto;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="REPLY")
@Entity
public class ReplyEntity {
    @Id
    @Column(name="REPLY_NUM", nullable = false)
    private int replyNum;
    @Column(name="REPLY_WRITER", nullable = false)
    private String replyWriter;
    @Column(name="REPLY_TITLE", nullable = false)
    private String replyTitle;
    @Column(name="REPLY_CONTENT", nullable = false)
    private String replyContent;
    @Column(name="BOARD_REF", nullable = false)
    private int boardRef;
    @Column(name="REPLY_REPLY_REF", nullable = false)
    private int replyReplyRef;
    @Column(name="REPLY_LEV", nullable = false)
    private int replyLev;
    @Column(name="REPLY_SEQ", nullable = false)
    private int replySeq;
    @Column(name="REPLY_READCOUNT", nullable = false)
    private int replyReadCount;
    @Column(name="REPLY_DATE", nullable = false)
    private Date replyDate;


    public ReplyDto toDto(){
        return ReplyDto.builder()
                .replyNum(this.replyNum)
                .replyWriter(this.replyWriter)
                .replyTitle(this.replyTitle)
                .replyContent(this.replyContent)
                .boardRef(this.boardRef)
                .replyReplyRef(this.replyReplyRef)
                .replyLev(this.replyLev)
                .replySeq(this.replySeq)
                .replyReadCount(this.replyReadCount)
                .replyDate(this.replyDate)
                .build();
    }


}
