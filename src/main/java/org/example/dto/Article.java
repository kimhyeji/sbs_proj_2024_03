package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Article extends Dto {
    public String title;
    public String body;
    public int hit;
    public int memberId;
    public int boardId;

    public Article(int memberId, int boardId, String title, String body, int hit) {
        this.memberId = memberId;
        this.boardId = boardId;
        this.title = title;
        this.body = body;
        this.hit = hit;
    }

    public Article(int memberId, int boardId, String title, String body) {
        this(memberId, boardId, title, body, 0);
    }

    public Article(Map<String, Object> row) {
        super(row);
        this.title = (String) row.get("title");
        this.body = (String) row.get("body");
        this.memberId = (int) row.get("memberId");
        this.boardId = (int) row.get("boardId");
//        this.hit = (int) row.get("hit");
    }

    public void increaseHit() {
        hit++;
    }
}
