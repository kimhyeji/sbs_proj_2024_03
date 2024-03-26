package org.example.dao;

import org.example.dto.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao extends Dao {
    private List<Article> articles;

    public ArticleDao() {
        articles = new ArrayList<>();
    }

    public void write(Article article) {
        articles.add(article);
        lastId = article.id;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public int getArticleIndexById(int id) {
        int i = 0;

        for ( Article article : articles ) {
            if ( article.id == id ) {
                return i;
            }
            i++;
        }

        return -1;
    }

    public Article getArticleById(int id) {
        int index = getArticleIndexById(id);

        if ( index != -1 ) {
            return articles.get(index);
        }

        return null;
    }

    public List<Article> getForPrintArticles(String searchKeyword) {
        if (searchKeyword != null && searchKeyword.length() != 0) {
            List<Article> forListArticles = new ArrayList<>();

            for (Article article : articles) {
                if (article.title.contains(searchKeyword)) {
                    forListArticles.add(article);
                }
            }

            return forListArticles;
        }

        return articles;
    }

    public void remove(Article foundArticle) {
        articles.remove(foundArticle);
    }
}
