package app.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private String email;
    private List<Cupcake> orderContent = new ArrayList<>();
    private int totalPrice;
    private LocalDate orderDate;


    public Order(ArrayList<Cupcake> content, int totalPrice, LocalDate time) {
        this.orderContent = content;
        this.totalPrice = totalPrice;
        this.orderDate = time;
    }
  
    public Order(int orderId, String email, List<Cupcake> orderContent, int totalPrice, LocalDate time) {
        this.orderId = orderId;
        this.email = email;
        this.orderContent = orderContent;
        this.totalPrice = totalPrice;
        this.orderDate = time;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getEmail() {
        return email;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public List<Cupcake> getOrderContent(){
        return orderContent;
    }

    public void setOrderContent(List<Cupcake> orderContent) {
        this.orderContent = orderContent;
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
