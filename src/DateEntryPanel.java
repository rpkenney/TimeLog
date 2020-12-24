import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class DateEntryPanel extends JPanel{

    public static final int DAY_MONTH_YEAR = 0;

    public static final int MONTH_YEAR = 1;

    public static final int YEAR = 2;

    public static final int NUM_MONTHS = 12;

     /** the number of days in the months, by monthv */
     public static final int[] NUM_DAYS_IN_MONTH = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

     /** the names of the months */
     public static final String[] MONTH_NAMES = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 

    private JTextField dayInput;

    private JTextField monthInput;

    private JTextField yearInput;

    private int format;

    private TimeLogger timeLogger;

    public DateEntryPanel(int format, TimeLogger timeLogger){
        this.setLayout(new FlowLayout());
        this.timeLogger = timeLogger;
        this.format = format;

        dayInput = new JTextField(Integer.toString(timeLogger.getCurrentDay()));

        monthInput = new JTextField(Integer.toString(timeLogger.getCurrentMonth()));

        yearInput = new JTextField(Integer.toString(timeLogger.getCurrentYear()));

        if(format == DAY_MONTH_YEAR){
            add(new JLabel("Day:"));
            add(dayInput);
        }
        if(format == DAY_MONTH_YEAR || format == MONTH_YEAR){
            add(new JLabel("Month:"));
            add(monthInput);
        }
        add(new JLabel("Year:"));
        add(yearInput);
    }

    public boolean isValidDate(){
        if(format == DAY_MONTH_YEAR && isValidDay() && isValidMonth() && isValidYear()){
            return true;
        } else if(format == MONTH_YEAR && format == MONTH_YEAR && isValidMonth() && isValidYear()){
            return true;
        } else if(format == YEAR && isValidYear()){
            return true;
        }
        return false;
    }
    
    public boolean isValidDay(){
        int day = getDay();
        int month = getMonth();
        if(day >= 0 && day <= NUM_DAYS_IN_MONTH[month]){
            return true;
        }
        return false;
    }

    public boolean isValidMonth(){
        int month = getMonth();
        if(month >= 1 && month <= NUM_MONTHS){
            return true;
        }
        return false;
    }

    public boolean isValidYear(){
        int year = getYear();
        if(year >= timeLogger.getCurrentYear()){
            return true;
        }
        return false;
    }

    public Date getDate(){
        return new Date(getDay(), getMonth(), getYear());
    }

    public int getDay(){
        return inputToInt(dayInput.getText());
    }

    public int getMonth(){
        return inputToInt(monthInput.getText());
    }

    public int getYear(){
        return inputToInt(yearInput.getText());
    }

    public int inputToInt(String input){
        Scanner scnr = new Scanner(input);
        if(scnr.hasNextInt()){
            return scnr.nextInt();
        }
        return 0;
    }
}