package org.ict.boot_react.security.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputUser {    // 로그인한 데이터를 가진 객체로 사용하기 위해 준비함
    // private String email, password;
    private String userid, password;

//    public InputUser(String username) {
//        this.email = username;
//    }

    public InputUser(String username){
        this.userid = username;
    }
}
