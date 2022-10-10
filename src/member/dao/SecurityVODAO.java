package member.dao;

import member.dto.SecurityVO;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SecurityVODAO {
    Connection conn;
    private static SecurityVODAO instance;

    private SecurityVODAO() {
        conn = DBUtil.getInstance().getConnection();
        init();
    }

    public static SecurityVODAO getInstance() {
        if (instance == null) instance = new SecurityVODAO();
        return instance;
    }

    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS SecurityVO(" +
                "userId varchar(50)," +
                "uuid varchar(50)," +
                "salt varchar(50))";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertSecurity(SecurityVO securityVO) {
        String sql = "insert into SecurityVO (userId, uuid, salt) values (?, ?, ?)";
        int idx = 0;
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(++idx, securityVO.getUserId());
            psmt.setString(++idx, securityVO.getUuid());
            psmt.setString(++idx, securityVO.getSalt());
           return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
