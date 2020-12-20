public class Date{

    public static final int NUM_QUANTITIES = 3;

    private int day;
    
    private int month;

    private int year;

    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String toString(){
        String str = "";
        str += day;
        str += " ";
        str += month;
        str += " ";
        str += year;
        return str;
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
}