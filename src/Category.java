/**
 * Category.java
 * 
 * This class maintains information one of the various
 * categories of work that can be done for clients
 * 
 * @author Robert Kenney
 */
public class Category{

    /** the name of the category */
    private String name;

    /** the description for the category */
    private String description;

    /** the quantity of the category done in a given session of work */
    private int quantity;

    /**
     * this constructor initializes the instance variables of the category
     * @param name the name of the category
     * @param description the description of the category
     * @param quantity the quantity of the category
     */
    public Category(String name, String description, int quantity){
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    /**
     * this method gets the description of the category
     * @return the description for the category
     */
    public String getDescription(){
        return description;
    }

    /**
     * this method gets the quantity of the category
     * @return the quantity for the category
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * this method returns whether the cateogory has a quantity
     * @return true if quantity is greater than 0, false otherwise
     */
    public boolean hasQuantity(){
        if(quantity > 0){
            return true;
        }
        return false;
    }
}