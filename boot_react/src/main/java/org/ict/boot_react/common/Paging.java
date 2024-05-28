package org.ict.boot_react.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Paging /* implements java.io.Serializable */ {
	/* private static final long serialVersionUID = -8649527407666546438L; */

	private int startRow;    //페이지에 출력할 시작행
	private int endRow;	//페이지에 출력할 끝행
	private int listCount;	//총 목록 갯수
	private int limit;		//한 페이지에 출력할 목록 갯수
	private int currentPage;	//출력할 현재 페이지
	private int maxPage;	//총 페이지 수(마지막 페이지)
	private int startPage;	//페이지 그룹의 시작값
	private int endPage;	//페이지 그룹의 끝값
	private String urlMapping;  //페이지 숫자 클릭시 요청할 url 저장용
	
	//기본생성자 없음
	
	//매개변수 있는 생성자
	public Paging(int listCount, int currentPage, int limit, String urlMapping) {
		this.listCount = listCount;
		this.currentPage = currentPage;
		this.limit = limit;
		this.urlMapping = urlMapping;
	}
	
	//페이지 계산 메소드
	public void calculate() {
		//총 페이지수 계산 : 
		//목록의 총 갯수가 21개인 경우, 한 페이지에 출력할 목록이 10개인 경우 총 페이지 수는 3임
		//목록 1개도 한 페이지 차지함
		this.maxPage = (int)((double)listCount / limit + 0.9);
		
		//뷰 페이지 출력에 사용할 페이지 그룹의 시작값 지정
		//페이지 그룹의 페이지 숫자를 10개씩 출력한다면,
		//현재 페이지가 3이면 페이지 그룹은 1페이지부터 10페이지가 됨
		//현재 페이지가 25페이지이면 페이지 그룹은 21페이지부터 30페이지가 됨
		this.startPage = (int)(((double)currentPage / limit + 0.9) - 1) * limit + 1;		
		this.endPage = startPage + limit - 1;
		
		//마지막 그룹의 끝값은 마지막 페이지와 맞춤
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		
		//요청한 페이지의 출력될 목록의 행번호를 계산
		//한 페이지에 출력할 목록 갯수가 10개인 경우, 현재 페이지가 3페이지가 요청되었다면
		//행은 21행부터 30행이 됨
		this.startRow = (currentPage - 1) * limit + 1;
		this.endRow = startRow + limit - 1;
	}
}





