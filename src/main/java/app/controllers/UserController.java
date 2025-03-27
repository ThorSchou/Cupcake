package app.controllers;

import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
            "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "cupcake"
    );
    private static final UserMapper userMapper = new UserMapper(connectionPool);
    private static UserController controller = new UserController();

    public static void Routes(Javalin app){


    }

    public static void loginPage(Context ctx) {
        ctx.render("/login.html");
    }

    public static void loginUser(Context ctx) {
        String email = ctx.formParam("username");
        String password = ctx.formParam("password");

        User user = userMapper.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            ctx.sessionAttribute("user", user);
            ctx.redirect("/index");
        } else {
            ctx.sessionAttribute("Error", "Invalid username or password.");
            ctx.redirect("/login");
        }
    }

    public static void registerPage(Context ctx) {
        ctx.render("/register.html");
    }

    public static void registerUser(Context ctx) {
        String email = ctx.formParam("username");
        String password = ctx.formParam("password");

        User existingUser = userMapper.getUserByEmail(email);
        if (existingUser != null) {
            ctx.sessionAttribute("Error", "Username already exists.");
            ctx.redirect("/register");
        } else {
            userMapper.createUser(email, password);
            ctx.redirect("/login");
        }
    }

}
