package app.controllers;

import app.entities.Basket;
import app.entities.Cupcake;
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
        app.post("/checkout/removeCupcake", CheckoutController::removeCupcake); // New route
        app.post("/checkout/updateQuantity", CheckoutController::updateQuantity); // New route
        app.get("/checkout", CheckoutController::showCheckout); // New route for GET
    }

    public static void showCheckout(Context ctx) {
        Basket basket = ctx.sessionAttribute("basket");
        if (basket != null) {
            System.out.println("Basket contents: " + basket.getContent());
        } else {
            System.out.println("Basket is null");
        }
        ctx.render("checkout.html");
    }

    public static void removeCupcake(Context ctx) {
        Basket basket = ctx.sessionAttribute("basket");
        if (basket != null) {
            int index = Integer.parseInt(ctx.formParam("index"));
            basket.getContent().remove(index);
            ctx.sessionAttribute("basket", basket); // Update session
        }
        ctx.redirect("/checkout");
    }

    public static void updateQuantity(Context ctx) {
        Basket basket = ctx.sessionAttribute("basket");
        if (basket != null) {
            int index = Integer.parseInt(ctx.formParam("index"));
            String action = ctx.formParam("action");
            Cupcake cupcake = basket.getContent().get(index);
            int newAmount = cupcake.getAmount();
            if ("increase".equals(action)) {
                newAmount++;
            } else if ("decrease".equals(action) && newAmount > 1) {
                newAmount--;
            }
            cupcake.setAmount(newAmount);
            ctx.sessionAttribute("basket", basket); // Update session
        }
        ctx.redirect("/checkout");
    }

    public static void checkout(Context ctx) {
        User user = ctx.sessionAttribute("user");
        if (user == null) {
            ctx.redirect("/login");
            return;
        }

        Basket basket = ctx.sessionAttribute("basket");
        if (basket == null || basket.getContent().isEmpty()) {
            ctx.redirect("/index");
            return;
        }

        Order order = OrderController.createOrderFromBasket(ctx);
        try {
            checkoutPayment(ctx, order);
            orderMapper.createOrder(order, user);
            ctx.sessionAttribute("basket", null); // Clear basket after success
            ctx.redirect("/index?payment=success"); // Redirect with success param
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Betaling fejlet");
            ctx.render("checkout.html");
        }
    }

    public static void checkoutPayment(Context ctx, Order order) {
        String userIdStr = ctx.formParam("userId");
        if (userIdStr == null || userIdStr.isEmpty()) {
            ctx.redirect("/login"); // Handle case where userId is empty
            return;
        }
        int userId = Integer.parseInt(userIdStr);
        int amountToWithdraw = order.getTotalPrice();

        User targetUser = userMapper.getUserById(userId);
        if (targetUser != null) {
            if (targetUser.getBalance() < amountToWithdraw) {
                ctx.redirect("/checkout?error=insufficient_balance");
                return;
            }
            int newBalance = targetUser.getBalance() - amountToWithdraw;
            userMapper.updateUserBalance(targetUser, newBalance);
        }
    }
}
