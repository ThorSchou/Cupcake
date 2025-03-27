package app.persistence;


import app.entities.Topping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToppingMapper {

    private final ConnectionPool connectionPool;

    public ToppingMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Topping> getAllToppings() {
        List<Topping> toppings = new ArrayList<>();
        String sql = "SELECT * FROM toppings";


        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("topping_id");
                String name = rs.getString("topping");
                int price = rs.getInt("price");

                toppings.add(new Topping(id, name, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toppings;
    }

    public Topping getToppingById(int id) {

        String sql = "SELECT * FROM toppings WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

             ps.setInt(1, id);

             try (ResultSet rs = ps.executeQuery()) {

                 if (rs.next()) {
                     int toppingId = rs.getInt("topping_id");
                     String name = rs.getString("topping");
                     int price = rs.getInt("price");

                     return new Topping(toppingId, name, price);
                 }
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
