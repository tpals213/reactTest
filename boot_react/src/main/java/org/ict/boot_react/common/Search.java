package org.ict.boot_react.common;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Search {
	private String keyword;
	private int age;    // 나이 검색시
	private int startRow;  //페이지의 시작행
	private int endRow;   //페이지의 끝행
	private Date begin;  //가입날짜 검색시 시작날짜
	private Date end;	  //가입날짜 검색시 끝날짜

}
