package org.ict.boot_react.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.boot_react.member.jpa.entity.MemberEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data   // @ToString, @EqualsAndHashCode, @Getter, @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class MemberDto {

    private String userId;
    private String userPwd;
    private String userName;
    private String gender;
    private int age;
    private String phone;
    private String email;
    private Date enrollDate;
    private Date lastModified;
    private String signType;
    private String adminYn;
    private String loginOk;
    private String photoFileName;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
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
