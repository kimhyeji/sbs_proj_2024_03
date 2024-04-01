package org.example.container;

import org.example.controller.Session;
import org.example.dao.ArticleDao;
import org.example.dao.MemberDao;
import org.example.service.ArticleService;
import org.example.service.ExportService;
import org.example.service.MemberService;

public class Container {
    public static Session session;
    public static ArticleDao articleDao;
    public static MemberDao memberDao;
    public static ArticleService articleService;
    public static MemberService memberService;
    public static ExportService exportService;

    static {
        articleDao = new ArticleDao();
        memberDao = new MemberDao();
        articleService = new ArticleService();
        memberService = new MemberService();
        exportService = new ExportService();
    }

    public static Session getSession() {
        if ( session == null ) {
            session = new Session();
        }

        return session;
    }
}
