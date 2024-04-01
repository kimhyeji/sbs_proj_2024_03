package org.example.controller;

import org.example.container.Container;
import org.example.dto.Member;
import org.example.service.MemberService;
import org.example.util.Util;

import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {
    private Scanner sc;
    private String cmd;
    private String actionMethodName;
    private MemberService memberService;
    private Session session;

    public MemberController(Scanner sc) {
        this.sc = sc;

        memberService = Container.memberService;
        session = Container.getSession();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        switch ( actionMethodName ) {
            case "join":
                doJoin();
                break;
            case "login":
                doLogin();
                break;
            case "logout":
                doLogout();
                break;
            default:
                System.out.println("존재하지 않는 명령어 입니다.");
                break;
        }

    }

    public void doJoin() {
        String loginId = null;

        while ( true ) {
            System.out.printf("로그인 아이디 : ");
            loginId = sc.nextLine();

            if ( isJoinableLoginId(loginId) == false ) {
                System.out.printf("%s(은)는 이미 사용중인 아이디 입니다.\n", loginId);
                continue;
            }

            break;
        }

        String loginPw = null;
        String loginPwConfirm = null;

        while ( true ) {
            System.out.printf("로그인 비번 : ");
            loginPw = sc.nextLine();
            System.out.printf("로그인 비번확인 : ");
            loginPwConfirm = sc.nextLine();

            if ( loginPw.equals(loginPwConfirm) == false ) {
                System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
                continue;
            }

            break;
        }

        System.out.printf("이름 : ");
        String name = sc.nextLine();

        memberService.join(loginId, loginPw, name);

        System.out.printf("[%s]님! 회원가입이 완료되었습니다! 환영합니다^^~\n", name);
    }

    public void doLogin() {
        System.out.printf("로그인 아이디 : ");
        String loginId = sc.nextLine();
        System.out.printf("로그인 비번 : ");
        String loginPw = sc.nextLine();

        Member member = memberService.getMemberByLoginId(loginId);

        if ( member == null ) {
            System.out.println("해당회원은 존재하지 않습니다.");
            return;
        }

        if ( member.loginPw.equals(loginPw) == false ) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return;
        }

        session.setLoginedMember(member);
        Member loginedMember = session.getLoginedMember();

        System.out.printf("로그인 성공! %s님 환영합니다!\n", loginedMember.name);
    }

    private void doLogout() {
        session.setLoginedMember(null);
        System.out.println("로그아웃 되었습니다.");
    }

    private boolean isJoinableLoginId(String loginId) {
        Member member = memberService.getMemberByLoginId(loginId);

        if ( member == null ) {
            return true;
        }

        return false;
    }
}
