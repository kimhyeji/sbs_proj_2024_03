package org.example.dao;

import org.example.dto.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberDao extends Dao {
    public List<Member> members;

    public MemberDao() {
        members = new ArrayList<>();
    }

    public void join(Member member) {
        members.add(member);
        lastId++;
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
        int index = getMemberIndexByLoginId(loginId);

        if ( index == -1 ) {
            return null;
        }

        return members.get(index);
    }
}
