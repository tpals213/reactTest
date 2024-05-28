package org.ict.boot_react.member.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.member.model.dto.MemberDto;

import java.util.Date;
import java.util.GregorianCalendar;

@Table(name="MEMBER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // jpa가 관리함, repository와 연결됨
public class MemberEntity {
    @Id    // JPA 가 객체를 관리할 떄 식별할 기본키 저장
    // primary key 지정
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_bnum")
    // 사용 시 무조건 default로 입력되므로 주석 처리할 것
    @Column(name="USERID", nullable=false)
    private String userId;
    @Column(name="USERPWD", nullable=false)
    private String userPwd;
    @Column(name="USERNAME", nullable=false)
    private String userName;
    @Column(name="GENDER", nullable=false)
    private String gender;
    @Column(name = "AGE")
    private int age;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name="ENROLL_DATE")
    private Date enrollDate;
    @Column(name="LASTMODIFIED")
    private Date lastModified;
    @Column(name="SIGNTYPE")
    private String signType;
    @Column(name="ADMIN_YN")
    private String adminYn;
    @Column(name="LOGIN_OK")
    private String loginOk;
    @Column(name="PHOTO_FILENAME")
    private String photoFileName;


    @PrePersist    // jpa 로 가기전에 작동
    public void prePersist() {
        // boardDate에 현재 날짜 적용
        enrollDate = new GregorianCalendar().getGregorianChange();
        lastModified = new GregorianCalendar().getGregorianChange();
    }

    public MemberDto toDto() {
        return MemberDto.builder()
                .userId(this.userId)
                .userPwd(this.userPwd)
                .userName(this.userName)
                .gender(this.gender)
                .age(this.age)
                .phone(this.phone)
                .email(this.email)
                .enrollDate(this.enrollDate)
                .lastModified(this.lastModified)
                .signType(this.signType)
                .adminYn(this.adminYn)
                .loginOk(this.loginOk)
                .photoFileName(this.photoFileName)
                .build();
    }



}
