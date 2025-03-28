package app.persistence;

import app.entities.Cupcake;
import app.entities.Order;
import app.entities.User;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderMapper {
    private final ConnectionPool connectionPool;

    public OrderMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void createOrder(Order order, User user) {
        LocalDate localDate = LocalDate.now();
        String sql = "INSERT INTO orders (customer_id, date) VALUES (?, ?)";
        int generatedKey = 0;

        try(Connection connection = connectionPool.getConnection();
            //RETURN_GENERATED_KEYS makes the PreparedStatement return any auto-generated keys that were made
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, user.getId());
            ps.setString(2, localDate.toString());

            ps.executeUpdate();

            //Fetches the auto-generated key from the order and puts it in a variable
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        sql = "INSERT INTO orderdetails (order_id, bottom, topping, price, amount) VALUES (?, ?, ?, ?, ?)";


        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){

            for(Cupcake s : order.getOrderContent()){
                ps.setInt(1, generatedKey);
                ps.setString(2, s.getBottom().getName());
                ps.setString(3, s.getTopping().getName());
                ps.setInt(4, s.getPrice());
                ps.setInt(5, s.getAmount());

                ps.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Order getOrder(int orderId) {
        String sql = "SELECT * FROM orderdetails WHERE order_id = ?";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            int price = rs.getInt("price");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderId, ConnectionPool connectionPool) {

    }
}
