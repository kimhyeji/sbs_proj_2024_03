package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Member extends Dto {
    public String loginId;
    public String loginPw;
    public String name;

    public Member(int id, String regDate, String loginId, String loginPw, String name) {
        this.id = id;
        this.regDate = regDate;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
    }

    public Member(Map<String, Object> row) {
//        super(row);
        this.id = (int) row.get("id");
        this.loginId = (String) row.get("loginId");
        this.loginPw = (String) row.get("loginPw");
        this.name = (String) row.get("name");
    }
}
