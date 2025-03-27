package app.persistence;


import app.entities.Bottom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BottomMapper {

    private final ConnectionPool connectionPool;

    public BottomMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Bottom> getAllBottoms() {
        List<Bottom> bottoms = new ArrayList<>();
        String sql = "SELECT * FROM bottoms";


        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("bottom_id");
                String bottomName = rs.getString("bottom");
                int price = rs.getInt("price");

                bottoms.add(new Bottom(id, bottomName, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bottoms;
    }

    public Bottom getBottomById(int id) {

        String sql = "SELECT * FROM toppings WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    int bottomId = rs.getInt("bottom_id");
                    String bottomName = rs.getString("bottom");
                    int price = rs.getInt("price");

                    return new Bottom(bottomId, bottomName, price);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
