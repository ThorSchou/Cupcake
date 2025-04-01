package app.entities;

import java.util.ArrayList;

public class Basket {
    private ArrayList<Cupcake> basketContent = new ArrayList<>();
    private int totalPrice;

    public Basket() {}

    public int getTotalPrice(){
        int totalPrice = 0;
        for(Cupcake s : basketContent){
            totalPrice += s.getPrice();
        }
        return totalPrice;
    }

    public ArrayList<Cupcake> getContent(){
        return basketContent;
    }

    public void addContent(Cupcake cupcake){
        this.basketContent.add(cupcake);
    }

}
