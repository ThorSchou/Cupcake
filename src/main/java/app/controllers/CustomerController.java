package app.controllers;

import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class CustomerController {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
            "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "cupcake"
    );
    private static final UserMapper userMapper = new UserMapper(connectionPool);

    public static void Routes(Javalin app){

        app.post("/admin/updateBalance", CustomerController::updateUserBalance);

    }

    public static void updateUserBalance(Context ctx) {
        // Get the currently logged-in user (admin)
        User adminUser = ctx.sessionAttribute("user");
        if (adminUser == null || !adminUser.isAdmin()) {
            ctx.redirect("/index");
            return;
        }

        // Get form data
        int userId = Integer.parseInt(ctx.formParam("userId"));
        int newBalance = Integer.parseInt(ctx.formParam("newBalance"));

        // Get the target user and update balance
        User targetUser = userMapper.getUserById(userId);
        if (targetUser != null) {
            userMapper.updateUserBalance(targetUser, newBalance);
        }

        // Redirect back to the customer page
        ctx.redirect("/customers");
    }
}
