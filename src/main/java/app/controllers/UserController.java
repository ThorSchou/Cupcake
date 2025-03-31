package app.controllers;

import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class UserController {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
            "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "cupcake"
    );
    private static final UserMapper userMapper = new UserMapper(connectionPool);

    public static void Routes(Javalin app){

        app.get("/login", UserController::loginPage);
        app.post("/login", UserController::loginUser);
        app.get("/register", UserController::registerPage);
        app.post("/register", UserController::registerUser);
        app.get("/index",UserController::frontPage);

        app.get("/checkout", UserController::checkoutPage);


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

            System.out.println("User logged in: " + user.getEmail());
            System.out.println("Is Admin: " + user.isAdmin());

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
        String confirmPassword = ctx.formParam("confirm-password");


        User existingUser = userMapper.getUserByEmail(email);
        if (existingUser != null) {
            ctx.sessionAttribute("Error", "Username already exists.");
            ctx.redirect("/register");
            return;
        } else if (!password.equals(confirmPassword)){
            ctx.sessionAttribute("Error", "Passwords do not match.");
            ctx.redirect("/register");
        }
            else {
            userMapper.createUser(email, password);
            ctx.redirect("/login");
        }
    }

    public static void frontPage(Context ctx){
        User user = ctx.sessionAttribute("user");
        ctx.render("/index.html");
    }

    public static void checkoutPage(Context ctx){
        User user = ctx.sessionAttribute("user");
        ctx.render("/checkout.html");
    }

}
