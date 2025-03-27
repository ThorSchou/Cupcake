package app.persistence;

import app.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class OrderMapper {

    public void createOrder(Order order, User user, ConnectionPool connectionPool) {
        LocalDate localDate = LocalDate.now();
        String sql = "INSERT INTO orders (customer_id, date) VALUES (?, ?)";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, user.getId());
            ps.setString(2, localDate.toString());

            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
