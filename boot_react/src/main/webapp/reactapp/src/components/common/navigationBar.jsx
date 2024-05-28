import React, {useEffect} from "react";
import { Navbar, Nav, Container } from 'react-bootstrap';
import {observer} from 'mobx-react';
import {authStore} from '../../stores/authStore';
import {logout} from '../../axiosApi/MemberAxios'

const NavigationBar = observer(() => {
    const loggedIn = authStore.loggedIn;    // 로그인 상태값

    useEffect(() => {
        authStore.checkLoggedIn()
    }, []);
    // 로그아웃 핸들러
    const handleLogout = () => {
        logout().then(res =>{
            localStorage.clear();
            authStore.setLoggedIn(false);
        })
    };
    return (
        <Navbar collapseOnSelect expand='lg' bg="dark">
            <Container>
                <Navbar.Brand href="/">
                    <img src="/images/logo192.png" alt="Logo" style={{width: '10vh'}}/> 
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                <Navbar.Collapse id='responsive-navbar-nav'>
                    <Nav className="me-auto">
                        <Nav.Link href="/start">시작하기</Nav.Link>
                        <Nav.Link href={"/notices"}>공지사항</Nav.Link>
                        <Nav.Link href={"/boards"}>게시판</Nav.Link>
                    </Nav>
                    {
                        loggedIn ?
                            <Nav>
                                <Nav.Link onClick={handleLogout}>로그아웃</Nav.Link>
                                <Nav.Link href={"members/mypage"}>내 정보</Nav.Link>
                            </Nav>
                        :
                            <Nav>
                                <Nav.Link href={"/members/login"}>로그인</Nav.Link>
                                <Nav.Link href={"/members/"}>회원가입</Nav.Link>
                            </Nav>
                    }
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
});

export default NavigationBar