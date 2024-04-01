package org.example.service;

import org.example.container.Container;
import org.example.dao.MemberDao;
import org.example.dto.Member;

public class MemberService {
    private MemberDao memberDao;

    public MemberService() {
        memberDao = Container.memberDao;
    }

    public int join(String loginId, String loginPw, String name) {
        Member member = new Member(loginId, loginPw, name);
        return memberDao.join(member);
    }

    public Member getMemberByLoginId(String loginId) {
        return memberDao.getMemberByLoginId(loginId);
    }
    public String getMemberNameById(int memberId) {
        return null;
    }
}
