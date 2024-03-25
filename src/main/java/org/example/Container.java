package org.example;

import org.example.dao.ArticleDao;
import org.example.dao.MemberDao;
import org.example.service.ArticleService;

public class Container {
    public static ArticleDao articleDao;
    public static MemberDao memberDao;
    public static ArticleService articleService;

    static {
        articleDao = new ArticleDao();
        memberDao = new MemberDao();
        articleService = new ArticleService();
    }
}
