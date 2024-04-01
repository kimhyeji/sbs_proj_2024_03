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

    public void join(Member member) {
        members.add(member);
        lastId = member.id;
    }

    public int getMemberIndexByLoginId(String loginId) {
        int i = 0;

        for ( Member member : members ) {
            if ( member.loginId.equals(loginId) ) {
                return i;
            }
            i++;
        }

        return -1;
    }

    public Member getMemberByLoginId(String loginId) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM `member` "));
        sb.append(String.format("WHERE loginId = '%s' ", loginId));

        Map<String, Object> memberRow = dbConnection.selectRow((sb.toString()));

        return new Member(memberRow);
    }

    public String getMemberNameById(int id) {
        for ( Member member : members ) {
            if ( member.id == id ) {
                return member.name;
            }
        }

        return "";
    }
}
