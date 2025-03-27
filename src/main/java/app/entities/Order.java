package app.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private ArrayList<Cupcake> orderContent = new ArrayList<>();
    private int totalPrice;
    private LocalDateTime orderDate;

    public Order(ArrayList<Cupcake> content, int totalPrice, LocalDateTime time) {
        this.orderContent = content;
        this.totalPrice = totalPrice;
        this.orderDate = time;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<> getOrderContent(){
        return orderContent;
    }

    public String getOrderContentString(){
        String orderContentString = "";

        for(Cupcake s : orderContent){
            orderContentString += s.getToppingName() + " ";
            orderContentString += s.getBottomName() + " ";
            orderContentString += ":" + s.getAmount() + " \n";
        }
        return orderContentString;
    }
}
