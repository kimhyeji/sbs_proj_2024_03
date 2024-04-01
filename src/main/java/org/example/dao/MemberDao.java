package org.example.dao;

import org.example.container.Container;
import org.example.db.DBConnection;
import org.example.dto.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberDao extends Dao {
    public List<Member> members;
    private DBConnection dbConnection;

    public MemberDao() {
        members = new ArrayList<>();
        dbConnection = Container.getDBConnection();
    }

    public int join(Member member) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("INSERT INTO `member` "));
        sb.append(String.format("SET regDate = NOW(), "));
        sb.append(String.format("updateDate = NOW(), "));
        sb.append(String.format("loginId = '%s' ", member.loginId));
        sb.append(String.format("loginPw = '%s' ", member.loginPw));
        sb.append(String.format("`name` = '%s' ", member.name));

        return dbConnection.insert(sb.toString());
    }


    public Member getMemberByLoginId(String loginId) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM `member` "));
        sb.append(String.format("WHERE loginId = '%s' ", loginId));

        Map<String, Object> memberRow = dbConnection.selectRow((sb.toString()));

        return new Member(memberRow);
    }
}
