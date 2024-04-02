package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.dto.Board;
import org.example.dto.Member;
import org.example.service.ArticleService;
import org.example.service.MemberService;

import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller{
    private Scanner sc;
    private String cmd;
    private String actionMethodName;
    private ArticleService articleService;
    private MemberService memberService;
    private Session session;

    public ArticleController(Scanner sc) {
        this.sc = sc;
        articleService = Container.articleService;
        memberService = Container.memberService;
        session = Container.getSession();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        switch ( actionMethodName ) {
            case "write":
                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "modify":
                doModify();
                break;
            case "delete":
                doDelete();
                break;
            case "currentBoard":
                doCurrentBoard();
                break;
            case "changeBoard":
                doChangeBoard();
                break;
            default:
                System.out.println("존재하지 않는 명령어 입니다.");
                 break;
        }
    }

    private void doChangeBoard() {
        String[] cmdBits = cmd.split(" ");
        int boardId = Integer.parseInt(cmdBits[2]);

        Board board = articleService.getBoard(boardId);

        if ( board == null ) {
            System.out.println("해당 게시판은 존재하지 않습니다.");
        } else {
            System.out.printf("[%s] 게시판으로 변경되었습니다.\n", board.getName());
            session.setCurrentBoard(board);
        }
    }

    private void doCurrentBoard() {
        Board board = session.getCurrentBoard();
        System.out.printf("현재 게시판 : %s 게시판\n", board.getName());
    }

    public void doWrite() {
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        int memberId = session.getLoginedMember().getId();
        int boardId = session.getCurrentBoard().getId();

        int newId = articleService.write(memberId, boardId, title, body);

        System.out.printf("%d번 게시물이 생성되었습니다.\n", newId);
    }

    public void showList() {
//        String searchKeyword = cmd.substring("article list".length()).trim();
//        List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword);

//        if ( forPrintArticles.size() == 0 ) {
//            System.out.println("검색결과가 존재하지 않습니다.");
//            return;
//        }

        List<Article> forPrintArticles = articleService.getArticles();

        System.out.println("번호 |   작성자 | 조회 | 제목 ");
        for ( int i = forPrintArticles.size() - 1; i >= 0 ; i-- ) {
            Article article = forPrintArticles.get(i);
            String writerName = memberService.getMemberNameById(article.memberId);

            System.out.printf("%4d | %5s | %4d | %s\n", article.id, writerName, article.hit, article.title);
        }
    }

    public void showDetail() {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = articleService.getForPrintArticle(id);

        if ( foundArticle == null ) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        foundArticle.increaseHit();

        System.out.printf("번호 : %d\n", foundArticle.id);
        System.out.printf("날짜 : %s\n", foundArticle.regDate);
        System.out.printf("작성자 : %d\n", foundArticle.memberId);
        System.out.printf("제목 : %s\n", foundArticle.title);
        System.out.printf("내용 : %s\n", foundArticle.body);
        System.out.printf("조회 : %d\n", foundArticle.hit);
    }

    public void doModify() {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = articleService.getArticle(id);

        if ( foundArticle == null ) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        Member loginedMember = session.getLoginedMember();

        if ( foundArticle.memberId != loginedMember.id ) {
            System.out.printf("권한이 없습니다.\n");
            return;
        }

        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        articleService.modify(foundArticle.id, title, body);

        System.out.printf("%d번 게시물이 수정되었습니다.\n", foundArticle.id);
    }

    public void doDelete() {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = articleService.getArticle(id);

        if ( foundArticle == null ) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        Member loginedMember = session.getLoginedMember();

        if ( foundArticle.memberId != loginedMember.id ) {
            System.out.printf("권한이 없습니다.\n");
            return;
        }

        articleService.remove(foundArticle);

        System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
    }
}
