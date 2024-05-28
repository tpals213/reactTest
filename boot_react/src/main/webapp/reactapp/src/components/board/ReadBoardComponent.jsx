import React, { Component } from "react";
import BoardAxios from "../../axios/BoardAxios";
import { withRouter } from "../../withRouter";

class ReadBoardComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            boardNum: this.props.params.boardNum,
            board: {}
        }
    }

    componentDidMount() {
        console.log(this.state.boardNum);
        BoardAxios.getBoardDetail(this.state.boardNum).then(res => {
            this.setState({ board: res.data });
        });
    }

    goToList = () => {
        this.props.navigate('/'); // this.props.navigate로 수정
    }

    goToUpdate = (event) => {
        event.preventDefault();
        console.log("1번 : " + this.state.boardNum)
        this.props.navigate(`/create-board/${this.state.boardNum}`);
    }

    deleteView = async function () {
        if (window.confirm("정말로 글을 삭제하시겠습니까?\n 삭제된 글은 복구 할 수 없습니다.")) {
            BoardAxios.deleteBoard(this.state.boardNum).then(res => {
                console.log("delete result => " + JSON.stringify(res));
                if (res.status == 200) {
                    this.props.navigate('/');
                } else {
                    alert("글 삭제가 실패했습니다.");
                }
            });
        }
    }

    render() {
        return (
            <div>
                <div className="card col-md-6 offset-md-3">
                    <h3 className="text-center"> Read Detail</h3>
                    <div className="card-body">
                        <div className="row">
                            <div className="col">
                                <label>Title:</label> {this.state.board.boardTitle}
                            </div>
                        </div>
                        <div className="row">
                            <div className="col">
                                <label>Contents</label><br></br>
                                <textarea value={this.state.board.boardContent} readOnly />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col">
                                <label>Writer:</label> {this.state.board.boardWriter}
                            </div>
                        </div>


                        <button className="btn btn-primary" onClick={this.goToList.bind(this)}
                            style={{ marginLeft: "10px" }}>글 목록으로 이동</button>
                        <button className="btn btn-info" onClick={this.goToUpdate}
                            style={{ marginLeft: "10px" }}>글 수정</button>
                        <button className="btn btn-danger" onClick={() => this.deleteView()}
                            style={{ marginLeft: "10px" }}>글 삭제</button>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(ReadBoardComponent);
