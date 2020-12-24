public class Task{

    private String category;

    private String description;

    private int quantity;

    public Task(int quantity, String category, String description){
        this.category = category;
        this.description = description;
        this.quantity = quantity;
    }

    public String toString(){
        String str = "";
        str += quantity;
        str += "\n";
        str += category;
        str += "\n";
        str += description;
        return str;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }
}