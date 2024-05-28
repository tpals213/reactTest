//게시글 서비스 요청과 관련된 함수들을 작성함
//axios : 자바스크립트 ajax 요청과 같은 일을 수행하는 모듈임
import axios from "axios";

//기본 url 지정
const BOARD_BASE_URL = "http://localhost:9999/boards";

class BoardAxios {
  //각 서비스별 메서드 작성
  //게시글 목록 조회 요청 처리용
  getBoardList(page, limit) {
    console.log(page, limit, "페이징")
    return axios.get(`${BOARD_BASE_URL}/list?page=${page}&limit=${limit}`);
  }

  //게시글 상세보기 요청 처리용
  getBoardDetail(boardNum) {
    return axios.get(BOARD_BASE_URL + "/" + boardNum);
  }

  //새 게시글 등록 처리용
  postBoardInsert(board) {
    return axios.post(BOARD_BASE_URL, board)
  }

  //게시글 수정 처리용
  putBoardUpdate(boardNum, board) {
    return axios.put(`${BOARD_BASE_URL}/${boardNum}`, board);
  }

  //게시글 삭제 처리용
  deleteBoard(boardNum) {
    return axios.delete(BOARD_BASE_URL + "/" + boardNum); 
  }

  //게시글 검색
  getBoardTitle() {}

  getBoardWriter() {}

  getBoardDate() {}
} // class closed

//외부에서 이 클래스를 import해서 사용하게 하려면
export default new BoardAxios(); //객체를 내보냄
