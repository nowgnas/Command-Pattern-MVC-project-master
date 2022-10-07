package house.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HouseDao {
    Connection con;
    private static HouseDao instance;

    private HouseDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:15000/backpjt", "root", "1234");
        } catch (Exception e) {

        }
    }

    public static HouseDao getInstance() {
        if (instance == null) instance = new HouseDao();
        return instance;
    }


    public List<String> getGugun(String sido) {
        String sql = "select distinct gugunName from dongcode where sidoName = ? and gugunName is not null";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, sido);
            ResultSet rs = stmt.executeQuery();
            List<String> list = new ArrayList<>();

            while (rs.next()) {
                list.add(rs.getString("gugunName"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(); // 운영 서버에서는 지워야 한다
            return null;
        }
    }

    public List<String> getDong(String sido, String gugun) {
        String sql = "select distinct dongName from dongcode where sidoName = ? and gugunName = ? and dongName is not null";
        List<String> list = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, sido);
            stmt.setString(2, gugun);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("dongName"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDongCode(String sidoName, String gugunName, String dongName) {
        String sql = "select dongCode from dongcode where sidoName = ? and gugunName = ? and dongName = ?";
        String dongCode;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, sidoName);
            stmt.setString(2, gugunName);
            stmt.setString(3, dongName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dongCode = rs.getString("dongCode");
                return dongCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
