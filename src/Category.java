
public class Category{

    private String name;

    private String description;

    private int quantity;

    public Category(String name, String description, int quantity){
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public String toString(){
        String str = "";
        if(description != null){
            if(hasQuantity()){
                str += quantity;
                str += " ";
            }
            str += name;
            str += ": ";
        }
        return str;
    }

    public String getDescription(){
        return description;
    }

    public int getQuantity(){
        return quantity;
    }

    public boolean hasQuantity(){
        if(quantity > 0){
            return true;
        }
        return false;
    }
}