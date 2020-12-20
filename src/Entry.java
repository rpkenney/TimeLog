/**
 * Entry.java
 * 
 * This class handles an individual entry of data from the user,
 * along with storing this data
 * 
 * @author Robert Kenney
 */
public class Entry{

    /** The number of categories of work */
    public static final int NUM_CATEGORIES = 8;

    /** the names of the categories of work */
    public static final String[] CATEGORIES = {"E-Blasts", "News Posts", "Press Releases", "Social Media Posts", "Testimonials", "Virtual Tours", "Website", "Other"};

    /** the date the entry was made */
    private Date date;

    /** the categories which data was entered for */
    private Category[] categories;

    /** the time spend during that entry */
    private double timeSpent;

    /**
     * this constructor assigns values to the instance variables of the entry
     * these values should come from user input
     * @param date
     * @param categories
     * @param timeSpent
     */
    public Entry(Date date, Category[] categories, double timeSpent){
        this.date = date;
        this.timeSpent = timeSpent;
        this.categories = categories;
    }

    /**
     * returns the date of the entry
     * @return the date of the entry
     */
    public Date getDate(){
        return date;
    }

    /**
     * returns a category based on its index in the list
     * @param category the index of the category
     * @return the category
     */
    public Category getCategory(int category){
        return categories[category];
    }

    /**
     * returns the list of all categories
     * @return the list of all categories
     */
    public Category[] getCategories(){
        return categories;
    }

    /**
     * returns the amount of time spent for the entry
     * @return the amount of time spent for the entry
     */
    public double getTimeSpent(){
        return timeSpent;
    }

    /**
     * returns whether the date of the entry is in a certain month and year
     * @param month the month in question
     * @param year the year in question
     * @return true if the date is within the month and year, false otherwise
     */
    public boolean isDateWithin(int month, int year){
        return date.isWithin(month, year);
    }

    /**
     * returns whether an entry is within a year
     * @param year the year in question
     * @return true if the date is within the year, false otherwise
     */
    public boolean isDateWithin(int year){
        return date.isWithin(year);
    }
}