package app.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private ArrayList<Cupcake> orderContent = new ArrayList<>();
    private int totalPrice;
    private LocalDate orderDate;

    public Order(ArrayList<Cupcake> content, int totalPrice, LocalDate time) {
        this.orderContent = content;
        this.totalPrice = totalPrice;
        this.orderDate = time;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<Cupcake> getOrderContent(){
        return orderContent;
    }

    public String getOrderContentString(){
        String orderContentString = "";

        for(Cupcake s : orderContent){
            orderContentString += s.getTopping().getName() + " ";
            orderContentString += s.getBottom().getName() + " ";
            orderContentString += ":" + s.getAmount() + " \n";
        }
        return orderContentString;
    }
}
