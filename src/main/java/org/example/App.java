package org.example;

import org.example.controller.ArticleController;
import org.example.controller.Controller;
import org.example.controller.MemberController;

import java.util.Scanner;

public class App {
    public void start() {
        System.out.println("== 프로그램 시작 ==");

        Scanner sc = new Scanner(System.in);

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        articleController.makeTestData();
        memberController.makeTestData();

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

            String[] cmdBits = cmd.split(" "); // article write / member join
            String controllerName = cmdBits[0]; // article / member
            String actionMethodName = cmdBits[1]; // write / join

            Controller controller = null;

            if ( controllerName.equals("article") ) {
                controller = articleController;
            }
            else if ( controllerName.equals("member") ) {
                controller = memberController;
            }
            else {
                System.out.println("존재하지 않는 명령어입니다.");
                continue;
            }

            controller.doAction(cmd, actionMethodName);
        }

        sc.close();
        System.out.println("== 프로그램 끝 ==");
    }
}
