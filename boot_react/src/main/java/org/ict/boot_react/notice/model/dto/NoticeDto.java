package org.ict.boot_react.notice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.notice.jpa.entitiy.NoticeEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data   // @ToString, @EqualsAndHashCode, @Getter, @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class NoticeDto {
    private int noticeNo;
    private String noticeTitle;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date noticeDate;
    private String noticeWriter;
    private String noticeContent;
    private String originalFileName;
    private String renameFileName;
    private String importance;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date impEndDate;
    private int readCount;

    public NoticeEntity toEntity() {
        return NoticeEntity.builder()
                .noticeNo(this.noticeNo)
                .noticeTitle(this.noticeTitle)
                .noticeDate(this.noticeDate)
                .noticeWriter(this.noticeWriter)
                .noticeContent(this.noticeContent)
                .originalFilePath(this.originalFileName)
                .renameFilePath(this.renameFileName)
                .importance(this.importance)
                .impEndDate(this.impEndDate)
                .readCount(this.readCount)
                .build();
    }



}
