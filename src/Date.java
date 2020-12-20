/**
 * Date.java
 * 
 * This class represents an individual date, 
 * containing a day, month, and year
 */
public class Date{

    /** The number of quantities stored in the date(being day, month, year) */
    public static final int NUM_QUANTITIES = 3;

    /** the value of the day */
    private int day;
    
    /** the value of the month */
    private int month;

    /** the value of the year */
    private int year;

    /**
     * This constructor assigns values to the day, month and year
     * @param day the day
     * @param month the month
     * @param year the year
     */
    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * this method converts the date to a string, to be displayed in reports
     */
    public String toString(){
        String str = "";
        str += day;
        str += " ";
        str += month;
        str += " ";
        str += year;
        return str;
    }

    /**
     * This method returns whether the date is within a given month and year
     * @param month the month in question
     * @param year the year in question
     * @return true if the date is within the month and year, false otherwise
     */
    public boolean isWithin(int month, int year){
        if(this.month == month && this.year == year){
            return true;
        }
        return false;
    }
    
    /**
     * This method returns whether the date is within a given year
     * @param year true if the date is within the year, false otherwise
     * @return 
     */
    public boolean isWithin(int year){
        if(this.year == year){
            return true;
        }
        return false;
    }
}