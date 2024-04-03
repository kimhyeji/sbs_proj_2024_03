package org.example.service;


import org.example.container.Container;
import org.example.dto.Article;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.List;

public class ExportService {
    ArticleService articleService;
    MemberService memberService;

    public ExportService() {
        articleService = Container.articleService;
        memberService = Container.memberService;
    }

    public void makeHtml() {
        List<Article> articles = articleService.getArticles();

        for ( Article article : articles ) {
            Member member = memberService.getMember(article.memberId);

            String fileName = article.id + ".html";
            String html = "<meta charset=\"UTF-8\">";
            html += "<div>번호 : " + article.id + "</div>";
            html += "<div>날짜 : " + article.regDate + "</div>";
            html += "<div>작성자 : " + member.name + "</div>";
            html += "<div>제목 : " + article.title + "</div>";
            html += "<div>내용 : " + article.body + "</div>";
            if ( article.id > 1 ) {
                html += "<div><a href=\"" + (article.id - 1) + ".html\">이전글</a></div>";
            }
            html += "<div><a href=\"" + (article.id + 1) + ".html\">다음글</a></div>";
            Util.writeFileContents("exportHtml/" + fileName, html);
        }
    }
}
