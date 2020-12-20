import java.util.*;
import java.io.File;

/**
 * TimeLog.java
 * 
 * This class creates and maintains a timelog, consisting of multiple clients
 * 
 * @author Robert Kenney
 */
public class TimeLog{
    
    /** The number of months in a year */
    public static final int NUM_MONTHS = 12;

    /** The hourly rate of pay */
    public static final int HOURLY_RATE = 25;

    /** the number of days in the months, by monthv */
    public static final int[] NUM_DAYS_IN_MONTH = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /** the names of the months */
    public static final String[] MONTH_NAMES = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /** an arraylist containing the clients */
    private ArrayList<Client> clients;

    /** the number of clients being served */
    private int numClients;

    /**
     * This constructor initializes the timelog, by loading in 
     * all of the existing clients names, and setting the number of clients
     */
    public TimeLog(){
        clients = new ArrayList<Client>();
        File[] files = new File(Client.FILE_PATH).listFiles();
        for(int i = 0; i < files.length; i++){
            String name = files[i].getName();
            name = name.substring(0, name.length() - Client.FILE_EXTENSION.length());
            clients.add(new Client(name));
            numClients++;
        }
    }

    /**
     * This method generates the overall monthly report
     * summarzing data for all clients from the month
     * @param month the month of the report
     * @param year the year of the report
     * @return the report itself
     */
    public String generateMonthlyReport(int month, int year){
        String report = "";
        report += "Date Submitted: ";
        report += getCurrentMonth();
        report += "/";
        report += getCurrentDay();
        report += "/";
        report += Integer.toString(getCurrentYear()).substring(2, 4);
        report += "\n";
        report += "Total Hours: ";
        report += getTimeSpent(month, year);
        report += "\n";
        report += "Hourly Rate: $";
        report += HOURLY_RATE;
        report += "\n";
        report += "Total Pay: $";
        report += String.format("%.2f", getPay(month, year));
        report += "\n\n";
        for(int i = 0; i < numClients; i++){
            if(!clients.get(i).getMonthlyReport(month, year).isEmpty()){
                report += clients.get(i).getMonthlyReport(month, year);
                report += "\n";
            }
        }
        return report;
    }

    /**
     * This method generates the overall yearly report
     * summarzing the pay for each month
     * @param year
     * @return the report itself
     */
    public String generateYearlyReport(int year){
        String report = "";
        report += "Pay by Month for ";
        report += year;
        report += "\n\n";
        for(int month = 1; month <= NUM_MONTHS; month++){
            report += MONTH_NAMES[month];
            report += ": $";
            report += String.format("%.2f", getPay(month, year));
            report += "\n";
        }
        report += "\n";
        report += "Total: $";
        report += String.format("%.2f", getPay(year));
        return report;
    }

    /**
     * This method gets the total time spent in a given month
     * @param month the month in question
     * @param year the year of the month
     * @return the time spent working for all clients that month
     */
    public double getTimeSpent(int month, int year){
        double timeSpent = 0;
        for(int client = 0; client < numClients; client++){
            timeSpent += clients.get(client).getTimeSpent(month, year);
        }
        return timeSpent;
    }

    /**
     * This method gets the total time spent in a given year
     * @param year the year in question
     * @return the time spent working for all clients that year
     */
    public double getTimeSpent(int year){
        double timeSpent = 0;
        for(int client = 0; client < numClients; client++){
            timeSpent += clients.get(client).getTimeSpent(year);
        }
        return timeSpent;
    }

    /**
     * This method returns the pay in a given month
     * @param month the month in question
     * @param year the year of the month
     * @return the pay for the month
     */
    public double getPay(int month, int year){
        double pay = getTimeSpent(month, year) * HOURLY_RATE;

        return pay;
    }

    /**
     * This method returns the pay in a given year
     * @param year the year in question
     * @return the total pay for that year
     */
    public double getPay(int year){
        double pay = HOURLY_RATE * getTimeSpent(year);

        return pay;
    }

    /**
     * this method adds a client to the clientlist
     * along with increasing the number of clients
     * @param name the name of the new client
     */
    public void addClient(String name){
        clients.add(new Client(name));
        numClients++;
    }

    /**
     * This method removes a client from the list
     * should they stop being served
     * @param index the index of the client in the list
     */
    public void removeClient(int index){
        clients.get(index).delete();
        clients.remove(index);
        numClients--;
    }

    /**
     * this mothod returns a client based on their name
     * @param name the name of the client in question
     * @return the Client
     */
    public Client getClient(String name){
        for(int client = 0; client < numClients; client++){
            if(clients.get(client).getName().equals(name)){
                return clients.get(client);
            }
        }

        return null;
    }

    /**
     * this mothod returns a client based on their index
     * @param index the index of the client in the list
     * @return the Client
     */
    public Client getClient(int index){
        return clients.get(index);
    }

    /**
     * This method returns a list of all of the names of the clients
     * @return a list of the names of all the clients
     */
    public String[] getClientNames(){
        String[] clientNameList = new String[numClients];
        for(int client = 0; client < numClients; client++){
            clientNameList[client] = clients.get(client).getName();
        }
        return clientNameList; 
    }

    /**
     * The number of clients in service
     * @return the number of clients in service
     */
    public int getNumClients(){
        return numClients;
    }

    /**
     * Gets the current day of the month
     * @return the current day of the month
     */
    public int getCurrentDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Gets the current month of the year
     * @return the current month of the year
     */
    public int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * Gets the current year
     * @return the current year
     */
    public int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Returns whether the entered time spent on a task 
     * for a client is valid
     * @param time the time spent
     * @return true if time spent is greater than 0, false otherwise
     */
    public boolean isValidTime(double time){
        if(time > 0){
            return true;
        }
        return false;
    }

    /**
     * Returns wheter the entered year for a task 
     *  is valid
     * @param time the year
     * @return true if the year is the current year or in the future, false otherwise
     */
    public boolean isValidYear(int year){
        if(year >= getCurrentYear()){
            return true;
        }
        return false;
    }

    /**
     * returns whether the entered month for a task is valid or not
     * @param month the month of the task
     * @return true if the month is greater than or equal to 1 and less than or equal to the number of months in a year, false otherwise
     */
    public boolean isValidMonth(int month){
        if(month >= 1 && month <= NUM_MONTHS){
            return true;
        }
        return false;
    }

    /**
     * returns whether the entered day for a task is valid or not
     * based on the day and month it was on
     * @param day the day
     * @param month the month
     * @return true if the day is greater than or equal to 1, and less than or equal to the number of days in that month, false otherwise.
     */
    public boolean isValidDay(int day, int month){
        if(day >= 0 && day <= NUM_DAYS_IN_MONTH[month]){
            return true;
        }
        return false;
    }

    /**
     * returns whether the entered quanitity of tasks done is valid or not
     * @param quantity the quantity of a task done
     * @ true if the quantity is greater than 0, false otherwise
     */
    public boolean isValidQuantity(int quantity){
        if(quantity >= 0){
            return true;
        }
        return false;
    }
}