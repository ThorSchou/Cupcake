package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrdersController {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
            "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "cupcake"
    );
    private static final OrderMapper orderMapper = new OrderMapper(connectionPool);

    public static void Routes(Javalin app) {
        app.get("/orders", OrdersController::showOrders);
        app.post("/admin/deleteOrder", OrdersController::deleteOrder);
    }

    public static void showOrders(Context ctx) {
        User adminUser = ctx.sessionAttribute("user");
        if (adminUser == null || !adminUser.isAdmin()) {
            ctx.redirect("/index");
            return;
        }

        List<Order> orders = orderMapper.getAllOrders();

        orders.forEach(order -> System.out.println("Order Content: " + order.getOrderContent()));

        ctx.render("orders.html", Map.of("orders", orders, "noOrders", orders.isEmpty()));
    }



    public static void deleteOrder(Context ctx) {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        orderMapper.deleteOrder(orderId);
        ctx.redirect("/orders");
    }
}
