package app.entities;

public class Bottom {

    private int id;
    private String bottomName;
    private int price;

    public Bottom(int id, String bottomName, int price){
        this.id = id;
        this.bottomName = bottomName;
        this.price = price;
    }

    public int getId(){
        return id;
    }

    public int getPrice(){
        return price;
    }

    public String getName(){
        return bottomName;
    }
}
