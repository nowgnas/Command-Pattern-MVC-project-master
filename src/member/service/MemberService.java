package member.service;

import member.dao.MemberDao;
import member.dto.Member;
import member.dto.SecurityVO;
import util.CipherUtil;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class MemberService {
    MemberDao memberDao;
    SecurityVOService securityVOService;
    private static MemberService instance;

    private MemberService() {
        memberDao = MemberDao.getInstance();
        securityVOService = SecurityVOService.getInstance();
    }

    public static MemberService getInstance() {
        if (instance == null) instance = new MemberService();
        return instance;
    }

    public Member login(String id, String pw) {
        return memberDao.login(id, pw);
    }

    public int memberInsert(Member member) {
        try {
            byte[] key = CipherUtil.generateKey("AES", 128);
            SecurityVO securityVO = new SecurityVO(member.getId(), UUID.randomUUID().toString(), CipherUtil.byteArrayToHex(key));
            securityVOService.insertSecurity(securityVO);

            member.setName(CipherUtil.aesEncrypt(member.getName(), key));
            member.setPw(new String(CipherUtil.getSHA256(member.getPw(), securityVO.getSalt())));
            return memberDao.memberInsert(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
