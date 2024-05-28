package org.ict.boot_react.board.model.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.board.jpa.entity.ReplyEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data   //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ReplyDto {
    private int replyNum;
    private String replyWriter;
    private String replyTitle;
    private String replyContent;
    private int boardRef;
    private int replyReplyRef;
    private int replyLev;
    private int replySeq;
    private int replyReadCount;
    private Date replyDate;

    public ReplyEntity toEntity(){
        return ReplyEntity.builder()
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
