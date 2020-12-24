public class Date{

    public static final String DISPLAY = "/";

    public static final String SAVE = " ";

    public static final int NUM_MONTHS = 12;

    public static final int DAYS_IN_MONTH = 31;

    public static final String[] MONTH_NAMES = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private int day;

    private int month;

    private int year;

    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public boolean isWithin(int month, int year){
        if(this.month == month && this.year == year){
            return true;
        }
        return false;
    }
    
    public boolean isWithin(int year){
        if(this.year == year){
            return true;
        }
        return false;
    }

    public boolean equals(Object o) {
        if (o instanceof Date) {
            Date other = (Date)o;
            if(day == other.day && month == other.month && year == other.year) {   
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public String toString(String seperator){
        String str = "";
        str += day;
        str += seperator;
        str += month;
        str += seperator;
        str += year;

        return str;
    }

    public String toMonthDayYear(){
        String str = "";
        str += month;
        str += DISPLAY;
        str += day;
        str += DISPLAY;
        str += year;
        
        return str;
    }

    public int getDay(){
        return day;
    }

    public int getMonth(){
        return month;
    }

    public int getYear(){
        return year;
    }
}