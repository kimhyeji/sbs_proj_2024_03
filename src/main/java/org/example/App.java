package org.example;

import org.example.controller.ArticleController;
import org.example.controller.MemberController;
import org.example.dto.Article;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public void start() {
        System.out.println("== 프로그램 시작 ==");


        Scanner sc = new Scanner(System.in);

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);
        articleController.makeTestData();

        while ( true ) {
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();
            cmd = cmd.trim();

            if ( cmd.length() == 0 ) {
                continue;
            }

            if ( cmd.equals("exit") ) {
                break;
            }

            if ( cmd.equals("member join") ) {
                memberController.doJoin();
            }
            else if ( cmd.equals("article write") ) {
                articleController.doWrite();
            }
            else if ( cmd.startsWith("article list") ) {
                articleController.showList(cmd);
            }
            else if ( cmd.startsWith("article detail ") ) {
                articleController.showDetail(cmd);
            }
            else if ( cmd.startsWith("article modify ") ) {
                articleController.doModify(cmd);
            }
            else if ( cmd.startsWith("article delete ") ) {
                articleController.doDelete(cmd);
            }
            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", cmd);
            }
        }

        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}
