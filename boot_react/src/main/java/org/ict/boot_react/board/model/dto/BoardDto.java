package org.ict.boot_react.board.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.board.jpa.entity.BoardEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data   //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class BoardDto {
    //게시글 원글
    //프로퍼티(property, == 멤버변수, field)
    private int boardNum;
    private String boardWriter;
    private String boardTitle;
    private String boardContent;

    private String boardOriginalFilename;

    private String boardRenameFilename;

    private int boardReadcount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date boardDate;
    
    //dto --> entity 로 변환하는 메소드 추가함
    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .boardNum(this.boardNum)
                .boardWriter(this.boardWriter)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardOriginalFilename(this.boardOriginalFilename)
                .boardRenameFilename(this.boardRenameFilename)
                .boardDate(this.boardDate)
                .boardReadcount(this.boardReadcount)
                .build();
    }

}
