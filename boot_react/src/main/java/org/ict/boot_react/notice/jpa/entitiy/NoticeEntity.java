package org.ict.boot_react.notice.jpa.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.notice.model.dto.NoticeDto;

import java.util.Date;

@Table(name="NOTICE")
@Data
@SequenceGenerator(name="notice_seq_nnum", sequenceName = "seq_nnum", initialValue = 13, allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // jpa가 관리함, repository와 연결됨
public class NoticeEntity {
    @Id    // JPA 가 객체를 관리할 떄 식별할 기본키 저장
    // primary key 지정
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_bnum")
	// 사용 시 무조건 default로 입력되므로 주석 처리할 것
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_seq_nnum")
    @Column(name="NOTICENO", nullable=false)
	private int noticeNo;
	@Column(name="NOTICETITLE", nullable=false)
	private String noticeTitle;
	@Column(name="NOTICEDATE", nullable=false)
	private Date noticeDate;
	@Column(name="NOTICEWRITER", nullable=false)
	private String noticeWriter;
    @Column(name = "NOTICECONTENT")
    private String noticeContent;
    @Column(name = "ORIGINAL_FILEPATH")
    private String originalFilePath;
    @Column(name = "RENAME_FILEPATH")
    private String renameFilePath;
	@Column(name="IMPORTANCE")
	private String importance;
    @Column(name = "IMP_END_DATE")
    private Date impEndDate;
    @Column(name = "READCOUNT")
    private int readCount;

	@PrePersist    // jpa 로 가기전에 작동
	public void prePersist() {
		// boardDate에 현재 날짜 적용
		noticeDate = new Date(System.currentTimeMillis());
        impEndDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 3));
	}

	// entity -> dto 로 변환하는 메소드 추가함
    public NoticeDto toDto() {
		return NoticeDto.builder()
                .noticeNo(this.noticeNo)
                .noticeTitle(this.noticeTitle)
                .noticeDate(this.noticeDate)
                .noticeWriter(this.noticeWriter)
                .noticeContent(this.noticeContent)
                .originalFileName(this.originalFilePath)
                .renameFileName(this.renameFilePath)
                .importance(this.importance)
                .impEndDate(this.impEndDate)
                .readCount(this.readCount)
				.build();
	}
}
