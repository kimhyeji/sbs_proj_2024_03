package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.dto.ArticleReply;
import org.example.dto.Board;
import org.example.dto.Member;
import org.example.service.ArticleService;
import org.example.service.MemberService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller{
    private Scanner sc;
    private String cmd;
    private ArticleService articleService;
    private MemberService memberService;
    private Session session;

    public ArticleController() {
        sc = Container.getScanner();
        session = Container.getSession();
        articleService = Container.articleService;
        memberService = Container.memberService;
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

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
        System.out.println("1. 공지 게시판");
        System.out.println("2. 자유 게시판");
        System.out.print("게시판 번호를 입력하세요) ");

        int boardId = checkScNum();
        Board board = articleService.getBoard(boardId);

        if ( board == null ) {
            System.out.println("해당 게시판은 존재하지 않습니다.");
        } else {
            System.out.printf("[%s 게시판]으로 변경되었습니다.\n", board.getName());
            session.setCurrentBoard(board);
        }
    }

    private void doCurrentBoard() {
        Board board = session.getCurrentBoard();
        System.out.printf("현재 게시판 : [%s 게시판]\n", board.getName());
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
        String searchKeyword = cmd.substring("article list".length()).trim();
        String boardCode = Container.getSession().getCurrentBoard().getCode();

        List<Article> forPrintArticles = articleService.getForPrintArticles(boardCode, searchKeyword);

        if ( forPrintArticles.size() == 0 ) {
            System.out.println("검색결과가 존재하지 않습니다.");
            return;
        }

        String boardName = Container.getSession().getCurrentBoard().getName();

        System.out.printf("[%s 게시판]\n", boardName);
        System.out.println("번호 |   작성자 | 조회 | 제목 ");
        for ( int i = forPrintArticles.size() - 1; i >= 0 ; i-- ) {
            Article article = forPrintArticles.get(i);
            Member member = memberService.getMember(article.memberId);

            System.out.printf("%4d | %5s | %4d | %s\n", article.id, member.name, article.hit, article.title);
        }
    }

    private boolean articleReplyAuthorityCheck() {
        System.out.println("1) 네 / 2) 아니오");
        System.out.printf("입력) ");
        String replyCheck = sc.nextLine();

        if ( replyCheck.equals("1") || replyCheck.equals("네") ) {
            if (session.isLogined() == false ) {
                System.out.println("로그인 후 이용 가능합니다.");
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public void showDetail() {
        System.out.print("게시물 번호를 입력하세요) ");

        int id = checkScNum();

        if ( id == 0 ) {
            return;
        }

        Article foundArticle = articleService.getForPrintArticle(id);

        if ( foundArticle == null ) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        foundArticle.increaseHit();

        Member member = memberService.getMember(foundArticle.memberId);

        System.out.printf("번호 : %d\n", foundArticle.id);
        System.out.printf("날짜 : %s\n", foundArticle.regDate);
        System.out.printf("작성자 : %s\n", member.name);
        System.out.printf("제목 : %s\n", foundArticle.title);
        System.out.printf("내용 : %s\n", foundArticle.body);
        System.out.printf("조회 : %d\n", foundArticle.hit);

        System.out.printf("== [%d번 게시물 댓글] ==\n", id);
        articleRepliesShowList(id);

        System.out.println("댓글을 작성 하시겠습니까?");
        boolean replyCheck = articleReplyAuthorityCheck();

        if ( replyCheck == false ){
            return;
        }

        if ( replyCheck ) {
            System.out.println("댓글을 입력 해주세요.");
            System.out.printf("입력) ");
            String replyBody = sc.nextLine();
            int memberId = session.getLoginedMember().getId();

            articleService.replyWrite(id, memberId, replyBody);
            System.out.println("댓글이 작성되었습니다.");

            articleRepliesShowList(id);
        }
    }

    private void articleRepliesShowList(int articleId) {
        List<ArticleReply> forPrintArticleReplies = articleService.getForPrintArticleReplies(articleId);

        System.out.printf("%d번 게시물 댓글\n", articleId);
        System.out.println("번호 |   작성자 | 제목 ");
        for ( int i = forPrintArticleReplies.size() - 1; i >= 0 ; i-- ) {
            ArticleReply reply = forPrintArticleReplies.get(i);
            Member replyMember = memberService.getMember(reply.memberId);

            System.out.printf("%4d | %5s | %s\n", reply.id, replyMember.name, reply.body);
        }
    }

    public void doModify() {
        System.out.print("수정할 게시물 번호를 입력하세요) ");
        
        int id = checkScNum();

        if ( id == 0 ) {
            return;
        }

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
        System.out.print("삭제할 게시물 번호를 입력하세요) ");
        
        int id = checkScNum();

        if ( id == 0 ) {
            return;
        }

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

        articleService.delete(foundArticle.id);

        System.out.printf("%d번 게시물이 삭제되었습니다.\n", foundArticle.id);
    }

    public int checkScNum() {
        int id = 0;

        try {
            id = sc.nextInt();
            sc.nextLine();
        } catch ( InputMismatchException e) {
            System.out.println("잘못 입력하셨습니다.");
            sc.nextLine();
            return 0;
        }

        return id;
    }
}
