import React, { Component } from "react";
import BoardAxios from "../../axiosApi/BoardAxios";
//컴포넌트 클래스는 반드시 리액트의 Component 클래스를 상속받아야 함
//컴포넌트는 한 페이지에 출력되는 내용을 담은 하나의 div 라고 생각하면 됨
//여기서는 게시글 목록 출력 div 를 컴포넌트로 작성함
class ListBoardComponent extends Component {
  constructor(props) {
    super(props); // 부모에게 매개변수를 넘김

    this.state = {
      //state 필드 선언
      //Json변수
      boards: [], //사이즈가 정해지지 않은 배열 선언
    };
  } //생성자

  componentDidMount() {
    //프론트 프레임워크에서 Mount라는 용어?
    //mount : axios 로 서버로 요청하고 받은 결과를 컴포넌트와 연결시키는 것
    //.then -> success 콜백  , res -> 받은 데이터
    //axios : 자바스크립트 ajax 통신을 처리하는 모듈
    BoardAxios.getBoardList().then((res) => {
      this.setState({ boards: res.data });
    });    
  }

  //출력 처리
  render() {
    return (
      <div>
        <h2 className="text-center">Boards List</h2>
        <div className="row">
          <table className="table table-striped table-bordered">
            <thead>
              <tr>
                <th>번호</th>
                <th>제목 </th>
                <th>작성자 </th>
                <th>작성일 </th>
                <th>첨부파일 </th>
                <th>조회수</th>
              </tr>
            </thead>
            <tbody>
              {this.state.boards.map((board) => (
                <tr key={board.boardNum}>
                  <td> {board.boardNum} </td>
                  <td>{board.boardTitle}</td>
                  <td> {board.boardWriter} </td>
                  <td> {board.boardDate} </td>
                  <td> {board.boardOriginalFilename} </td>
                  <td> {board.boardReadcount} </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    ); //return
  } //render()
} // class

export default ListBoardComponent;
