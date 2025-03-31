package app.persistence;

import app.entities.*;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        return null;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT public.orders.order_id, public.users.email, public.orders.date, SUM(public.orderdetails.price * public.orderdetails.amount) AS total_price " +
                "FROM public.orders " +
                "JOIN public.users ON public.orders.customer_id = public.users.user_id " +
                "JOIN public.orderdetails ON public.orders.order_id = public.orderdetails.order_id " +
                "GROUP BY public.orders.order_id, public.users.email, public.orders.date";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String email = rs.getString("email");
                int totalPrice = rs.getInt("total_price");
                LocalDateTime orderDate = rs.getTimestamp("date").toLocalDateTime();

                List<Cupcake> cupcakes = getCupcakesForOrder(orderId);

                // Debug print statement
                System.out.println("Order ID: " + orderId + ", Email: " + email + ", Total Price: " + totalPrice);

                orders.add(new Order(orderId, email, cupcakes, totalPrice, orderDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    private List<Cupcake> getCupcakesForOrder(int orderId) {
        List<Cupcake> cupcakes = new ArrayList<>();
        String sql = "SELECT public.bottoms.bottom_id, public.bottoms.bottom AS bottom_name, public.bottoms.price AS bottom_price, " +
                "public.toppings.topping_id, public.toppings.topping AS topping_name, public.toppings.price AS topping_price, " +
                "public.orderdetails.price, public.orderdetails.amount " +
                "FROM public.orderdetails " +
                "JOIN public.bottoms ON public.orderdetails.bottom = public.bottoms.bottom_id " +
                "JOIN public.toppings ON public.orderdetails.topping = public.toppings.topping_id " +
                "WHERE public.orderdetails.order_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bottom bottom = new Bottom(
                        rs.getInt("bottom_id"),
                        rs.getString("bottom_name"),
                        rs.getInt("bottom_price")
                );

                Topping topping = new Topping(
                        rs.getInt("topping_id"),
                        rs.getString("topping_name"),
                        rs.getInt("topping_price")
                );

                Cupcake cupcake = new Cupcake(
                        orderId,
                        topping,
                        bottom,
                        rs.getInt("price"),
                        rs.getInt("amount")
                );

                cupcakes.add(cupcake);

                // Debug print to confirm cupcakes are fetched
                System.out.println("Cupcake: " + topping.getName() + " + " + bottom.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cupcakes;
    }


    public void deleteOrder(int orderId) {
        String deleteOrderDetails = "DELETE FROM public.orderdetails WHERE order_id = ?";
        String deleteOrder = "DELETE FROM public.orders WHERE order_id = ?";  // Fixed the column name

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps1 = connection.prepareStatement(deleteOrderDetails);
                 PreparedStatement ps2 = connection.prepareStatement(deleteOrder)) {

                ps1.setInt(1, orderId);
                ps1.executeUpdate();

                ps2.setInt(1, orderId);
                ps2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}