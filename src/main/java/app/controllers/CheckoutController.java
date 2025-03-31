package app.controllers;

import app.entities.Basket;
import app.entities.Order;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class CheckoutController {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
            "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "cupcake"
    );
    private static final UserMapper userMapper = new UserMapper(connectionPool);
    private static final OrderMapper orderMapper = new OrderMapper(connectionPool);

    public static void Routes(Javalin app){
        app.post("/checkout/completeOrder", CheckoutController::checkout);
    }

    public static void checkout(Context ctx){

        User user = ctx.sessionAttribute("user");
        if(user == null){
            ctx.redirect("/login");
            return;
        }
        Order order = OrderController.createOrderFromBasket(ctx);
        checkoutPayment(ctx, order);
        orderMapper.createOrder(order, user);
    }

    public static void checkoutPayment(Context ctx, Order order) {

        int userId = Integer.parseInt(ctx.formParam("userId"));
        int amountToWithdraw = order.getTotalPrice();

        User targetUser = userMapper.getUserById(userId);
        if (targetUser != null) {
            int newBalance = targetUser.getBalance() - amountToWithdraw;
            userMapper.updateUserBalance(targetUser, newBalance);
        }

    }
}
