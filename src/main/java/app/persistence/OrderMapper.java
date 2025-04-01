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

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, user.getId());
            ps.setDate(2, java.sql.Date.valueOf(localDate)); // Convert LocalDate to java.sql.Date
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert into orders table", e);
        }

        sql = "INSERT INTO orderdetails (order_id, bottom, topping, price, amount) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Cupcake s : order.getOrderContent()) {
                ps.setInt(1, generatedKey);
                ps.setInt(2, s.getBottom().getId());
                ps.setInt(3, s.getTopping().getId());
                ps.setInt(4, s.getTopping().getPrice() + s.getBottom().getPrice()); // Per-unit price
                ps.setInt(5, s.getAmount());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert into orderdetails table", e);
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
        String sql = "SELECT public.orders.order_id, public.users.email, public.orders.date, " +
                "SUM(public.orderdetails.price * public.orderdetails.amount) AS total_price " +
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
                LocalDate orderDate = rs.getDate("date").toLocalDate();

                List<Cupcake> cupcakes = getCupcakesForOrder(orderId);

                // Debug print statement with cupcake details
                System.out.println("Order ID: " + orderId + ", Email: " + email + ", Total Price: " + totalPrice);
                cupcakes.forEach(cupcake ->
                        System.out.println("  Cupcake: " + cupcake.getTopping().getName() + " + " +
                                cupcake.getBottom().getName() + ", Price: " + cupcake.getPrice() +
                                ", Amount: " + cupcake.getAmount())
                );

                Order order = new Order(orderId, email, cupcakes, totalPrice, orderDate);
                // Verify total price matches cupcakes
                int calculatedTotal = cupcakes.stream()
                        .mapToInt(Cupcake::getPrice)
                        .sum();
                if (totalPrice != calculatedTotal) {
                    System.out.println("WARNING: Total price mismatch for Order " + orderId +
                            ". DB: " + totalPrice + ", Calculated: " + calculatedTotal);
                }

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch orders", e); // Improved error handling
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

                int orderDetailsPrice = rs.getInt("price"); // Per-unit price from orderdetails
                int amount = rs.getInt("amount");

                Cupcake cupcake = new Cupcake(
                        topping,
                        bottom,
                        orderDetailsPrice, // Per-unit price
                        amount
                );

                cupcakes.add(cupcake);

                // Enhanced debug print to verify pricing
                System.out.println("Cupcake: " + topping.getName() + " + " + bottom.getName() +
                        ", Unit Price: " + orderDetailsPrice +
                        ", Amount: " + amount +
                        ", Total Price: " + cupcake.getPrice());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch cupcakes for order " + orderId, e); // Improved error handling
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