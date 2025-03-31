package app.controllers;

import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class CustomerController {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
            "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "cupcake"
    );
    private static final UserMapper userMapper = new UserMapper(connectionPool);

    public static void Routes(Javalin app){
        app.get("/customers", CustomerController::showCustomers);
        app.post("/admin/updateBalance", CustomerController::updateUserBalance);

    }

    public static void showCustomers(Context ctx) {
        User adminUser = ctx.sessionAttribute("user");
        if (adminUser == null || !adminUser.isAdmin()) {
            ctx.redirect("/index");
            return;
        }

        List<User> customers = userMapper.getAllUsers();
        ctx.attribute("customers", customers);
        ctx.render("customers.html");
    }

    public static void updateUserBalance(Context ctx) {
        User adminUser = ctx.sessionAttribute("user");
        if (adminUser == null || !adminUser.isAdmin()) {
            ctx.redirect("/index");
            return;
        }

        int userId = Integer.parseInt(ctx.formParam("userId"));
        int amountToAdd = Integer.parseInt(ctx.formParam("amount"));

        User targetUser = userMapper.getUserById(userId);
        if (targetUser != null) {
            int newBalance = targetUser.getBalance() + amountToAdd;
            userMapper.updateUserBalance(targetUser, newBalance);
        }

        ctx.redirect("/customers");
    }
}
