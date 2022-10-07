package member.service;

import member.dao.MemberDao;
import member.dto.Member;

public class MemberService {
    MemberDao memberDao;
    private static MemberService instance;

    private MemberService() {
        memberDao = MemberDao.getInstance();
    }

    public static MemberService getInstance() {
        if (instance == null) instance = new MemberService();
        return instance;
    }

    public Member login(String id, String pw) {
        return memberDao.login(id, pw);
    }

    public int memberInsert(Member member) {
        return memberDao.memberInsert(member);
    }
}
