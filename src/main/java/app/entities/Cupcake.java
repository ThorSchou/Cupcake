package app.entities;

public class Cupcake {

    private Topping topping;
    private Bottom bottom;
    private int price;
    private int amount;

    public Cupcake(Topping topping, Bottom bottom, int price, int amount){
        this.topping = topping;
        this.bottom = bottom;
        this.price = price;
        this.amount = amount;

    }

//    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice(){
        return price * amount;
    }

    public int getAmount(){
        return amount;
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    public Bottom getBottom() {
        return bottom;
    }

    public void setBottom(Bottom bottom) {
        this.bottom = bottom;
    }
}
