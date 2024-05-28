import React, { Component } from 'react';
import BoardAxios from "../../axios/BoardAxios";
import { withRouter } from '../../withRouter';

class CreateBoardComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            boardNum: this.props.params.boardNum,
            boardTitle: '',
            boardContent: '',
            boardWriter: ''
        }

        this.changeBoardTitleHandler = this.changeBoardTitleHandler.bind(this);
        this.changeboardContentHandler = this.changeboardContentHandler.bind(this);
        this.changeboardWriterHandler = this.changeboardWriterHandler.bind(this);
        this.createBoard = this.createBoard.bind(this);
    }


    changeBoardTitleHandler = (event) => {
        this.setState({ boardTitle: event.target.value });
    }

    changeboardContentHandler = (event) => {
        this.setState({ boardContent: event.target.value });
    }

    changeboardWriterHandler = (event) => {
        this.setState({ boardWriter: event.target.value });
    }

    createBoard = (event) => {
        console.log("5번 : " + this.state.boardNum) 
        event.preventDefault();
        let board = {
            boardNym: this.state.boardNum,
            boardTitle: this.state.boardTitle,
            boardContent: this.state.boardContent,
            boardWriter: this.state.boardWriter
        };
        console.log("board => " + JSON.stringify(board));
        if (this.state.boardNum == '_create') {
            console.log("3번 : " + this.state.boardNum)
            BoardAxios.postBoardInsert(board).then(res => {
                this.props.navigate('/')
            });
        } else {
            BoardAxios.putBoardUpdate(this.state.boardNum, board).then(res => {
                this.props.navigate('/');

            });
        }
    }

    cancel() {
        this.props.navigate('/');
    }
    getTitle() {
        if (this.state.boardNum === '_create') {
            return <h3 className="text-center">새글을 작성해주세요</h3>
        } else {
            return <h3 className="text-center">{this.state.boardNum}번 글을 수정 합니다.</h3>
        }
    }

    componentDidMount() {
        if (this.state.boardNum === '_create') {
            return
        } else {
            BoardAxios.getBoardDetail(this.state.boardNum).then((res) => {
                let board = res.data;
                console.log("board => " + JSON.stringify(board));

                this.setState({
                    boardNum : board.boardNum,
                    boardTitle: board.boardTitle,
                    boardContent: board.boardContent,
                    boardWriter: board.boardWriter
                });
            });
        }
    }


    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            {this.getTitle()}
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label> Title </label>
                                        <input type="text" placeholder="boardTitle" name="title" className="form-control"
                                            value={this.state.boardTitle} onChange={this.changeBoardTitleHandler} />
                                    </div>
                                    <div className="form-group">
                                        <label> Contents </label>
                                        <textarea placeholder="boardContent" name="contents" className="form-control"
                                            value={this.state.boardContent} onChange={this.changeboardContentHandler} />
                                    </div>
                                    <div className="form-group">
                                        <label> boardWriter </label>
                                        <input placeholder="boardWriter" name="boardWriter" className="form-control"
                                            value={this.state.boardWriter} onChange={this.changeboardWriterHandler} />
                                    </div>
                                    <button className="btn btn-success" onClick={this.createBoard}>Save</button>
                                    <button className="btn btn-danger" onClick={this.cancel.bind(this)}
                                        style={{ marginLeft: "10px" }}>Cancel</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
export default withRouter(CreateBoardComponent);