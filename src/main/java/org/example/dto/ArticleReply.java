package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ArticleReply extends Dto {
    private int articleId;
    private int memberId;
    private String body;
}
