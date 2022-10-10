package member.service;

import member.dao.SecurityVODAO;
import member.dto.SecurityVO;

public class SecurityVOService {
    SecurityVODAO securityVODAO;
    private static SecurityVOService instance;

    private SecurityVOService() {
        securityVODAO = SecurityVODAO.getInstance();
    }

    public static SecurityVOService getInstance() {
        if (instance == null) instance = new SecurityVOService();
        return instance;
    }

    public int insertSecurity(SecurityVO securityVO) {
        return securityVODAO.insertSecurity(securityVO);
    }
}
