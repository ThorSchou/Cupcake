package app.controllers;

import app.entities.*;
import app.persistence.BottomMapper;

import app.persistence.ConnectionPool;
import app.persistence.ToppingMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.List;

public class OrderController {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(
            "postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=public", "cupcake"
    );
    private static final ToppingMapper toppingMapper = new ToppingMapper(connectionPool);
    private static final BottomMapper bottomMapper = new BottomMapper(connectionPool);


    public static void Routes(Javalin app){
        app.post("/addToBasket", OrderController::addToBasket);

    }

    public static void addToBasket(Context ctx){
        int bottomId = Integer.parseInt(ctx.formParam("bottom-selection"));
        int toppingId = Integer.parseInt(ctx.formParam("topping-selection"));
        int amount = Integer.parseInt(ctx.formParam("amount"));
        Basket basket = ctx.sessionAttribute("basket");

        //Checks if basket exists on the session, if not it creates one.
        if(basket == null){
            basket = new Basket();
            ctx.sessionAttribute("basket", basket);
        }

        Topping topping = toppingMapper.getToppingById(toppingId);
        Bottom bottom = bottomMapper.getBottomById(bottomId);
        int price = topping.getPrice() + bottom.getPrice();
        Cupcake cupcake = new Cupcake(topping, bottom, price, amount);
        basket.addContent(cupcake);

        ctx.redirect("/index.html");
    }

    public static Order createOrderFromBasket(Context ctx){
        Basket basket = ctx.sessionAttribute("basket");
        if(basket != null){
            Order order = new Order(basket.getContent(), basket.getTotalPrice(), LocalDate.now());
            return order;
        }

        ctx.render("/index.html");
        //MANGLER FEJLHÅNDTERING
        return null;
    }
}
