package org.example;

import org.example.dao.ArticleDao;
import org.example.dao.MemberDao;
import org.example.service.ArticleService;
import org.example.service.MemberService;

public class Container {
    public static ArticleDao articleDao;
    public static MemberDao memberDao;
    public static ArticleService articleService;
    public static MemberService memberService;

    static {
        articleDao = new ArticleDao();
        memberDao = new MemberDao();
        articleService = new ArticleService();
        memberService = new MemberService();
    }
}
