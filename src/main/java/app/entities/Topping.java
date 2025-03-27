package app.entities;

public class Topping {

    private int id;
    private String toppingName;
    private int price;

    public Topping(int id, String toppingName, int price){
        this.id = id;
        this.toppingName = toppingName;
        this.price = price;

    }

    public int getId(){
        return id;
    }

    public int getPrice(){
        return price;
    }

    public String getName(){
        return toppingName;
    }
}
