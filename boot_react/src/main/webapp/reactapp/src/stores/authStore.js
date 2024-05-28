import {makeAutoObservable} from 'mobx';
// 로그인 상태 자동 감시자 만들기

class AuthStore{
    loggedIn = false;
    isAdmin = false;
    constructor(){
        makeAutoObservable(this);
    }
    setLoggedIn(status){
        this.loggedIn = status
    }
    setIsAdmin(status){
        this.isAdmin = status
    }

    checkLoggedIn(){
        this.loggedIn = !!localStorage.getItem("token")
    }

}
export const authStore = new AuthStore();