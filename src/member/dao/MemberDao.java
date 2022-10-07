package member.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import member.dto.Member;

public class MemberDao {
    Connection con;
    private static MemberDao instance;

    private MemberDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:15000/backpjt", "root", "1234");
        } catch (Exception e) {

        }
    }

    public static MemberDao getInstance() {
        if (instance == null) instance = new MemberDao();
        return instance;
    }

    public Member login(String id, String pw) {
        String sql = "select * from member where email=? and password=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, pw);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Member(id, pw, rs.getString("name"));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int memberInsert(Member member) {
        String sql = "insert into member(id, password, name) values(?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, member.getId());
            stmt.setString(2, member.getPw());
            stmt.setString(3, member.getName());

            return stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}




