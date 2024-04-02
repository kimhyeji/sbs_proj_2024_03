package org.example.service;

import org.example.container.Container;
import org.example.dao.ArticleDao;
import org.example.dto.Article;
import org.example.dto.Board;
import java.util.List;

public class ArticleService {
    private ArticleDao articleDao;

    public ArticleService() {
        articleDao = Container.articleDao;
    }

    public List<Article> getForPrintArticles(String searchKeyword) {
        return articleDao.getForPrintArticles(searchKeyword);
    }

    public List<Article> getForPrintArticles() {
        return articleDao.getForPrintArticles(null);
    }

    public int write(int memberId, int boardId, String title, String body) {
        Article article = new Article(memberId, boardId, title, body);
        return articleDao.write(article);
    }

    public List<Article> getArticles() {
        return articleDao.getArticles();
    }

    public Article getArticle(int id) {
        return articleDao.getArticle(id);
    }

    public Board getBoard(int id) {
        return articleDao.getBoard(id);
    }

    public Article getForPrintArticle(int id) {
        return articleDao.getForPrintArticle(id);
    }

    public void modify(int id, String title, String body) {
        articleDao.modify(id, title, body);
    }

    public void delete(int id) {
        articleDao.delete(id);
    }
}
