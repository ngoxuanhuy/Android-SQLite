package ngoxuanhuy.tut11b;

/**
 * Created by ngoxuanhuy on 4/17/2017.
 */

public class Product {
    private String name = "";
    private String quantity = "";
    private String price = "";

    public String toString(){
        return ("Name: "    + getName() +"\n" +
                "Quantity: "+ getQuantity() + "\n"+
                "Price: " + getPrice()+"\n");
    }

    public String getName(){
        return this.name;
    }

    public String getQuantity(){
        return this.quantity;
    }

    public String getPrice(){
        return this.price;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setQuantity(String quantity){
        this.quantity = quantity;
    }

    public void setPrice(String price){
        this.price = price;
    }
}
