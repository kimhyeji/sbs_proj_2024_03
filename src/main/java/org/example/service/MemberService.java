package org.example.service;

import org.example.Container;
import org.example.dao.MemberDao;
import org.example.dto.Member;

public class MemberService {
    private MemberDao memberDao;

    public MemberService() {
        memberDao = Container.memberDao;
    }

    public void join(Member member) {
        memberDao.join(member);
    }

    public Member getMemberByLoginId(String loginId) {
        return memberDao.getMemberByLoginId(loginId);
    }

    public int getMemberIndexByLoginId(String loginId) {
        return memberDao.getMemberIndexByLoginId(loginId);
    }
}
