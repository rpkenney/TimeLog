import java.util.*;
import java.io.File;

public class TimeLog{
    
    public static final int NUM_MONTHS = 12;

    public static final int MAX_DAYS_IN_MONTH = 31;

    public static final int HOURLY_RATE = 25;

    public static final int[] monthsWith30Days = {11, 4, 6, 9};

    public static final int[] monthsWith31Days = {1, 3, 5, 7, 8, 10, 12};

    public static final String[] MONTH_NAMES = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private ArrayList<Client> clients;

    private int numClients;

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

    public double getTimeSpent(int month, int year){
        double timeSpent = 0;
        for(int client = 0; client < numClients; client++){
            timeSpent += clients.get(client).getTimeSpent(month, year);
        }
        return timeSpent;
    }

    public double getTimeSpent(int year){
        double timeSpent = 0;
        for(int client = 0; client < numClients; client++){
            timeSpent += clients.get(client).getTimeSpent(year);
        }
        return timeSpent;
    }

    public double getPay(int month, int year){
        double pay = getTimeSpent(month, year) * HOURLY_RATE;

        return pay;
    }

    public double getPay(int year){
        double pay = HOURLY_RATE * getTimeSpent(year);

        return pay;
    }
    

    public void addClient(String name){
        clients.add(new Client(name));
        numClients++;
    }

    public void removeClient(int index){
        clients.get(index).delete();
        clients.remove(index);
        numClients--;
    }

    public Client getClient(String name){
        for(int client = 0; client < numClients; client++){
            if(clients.get(client).getName().equals(name)){
                return clients.get(client);
            }
        }

        return null;
    }

    public Client getClient(int client){
        return clients.get(client);
    }

    public String[] getClientNames(){
        String[] clientNameList = new String[numClients];
        for(int client = 0; client < numClients; client++){
            clientNameList[client] = clients.get(client).getName();
        }
        return clientNameList; 
    }

    public int getNumClients(){
        return numClients;
    }

    public int getCurrentDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public boolean isValidTime(double time){
        if(time > 0){
            return true;
        }
        return false;
    }

    public boolean isValidYear(int year){
        if(year >= getCurrentYear()){
            return true;
        }
        return false;
    }

    public boolean isValidMonth(int month){
        if(month >= 1 && month <= NUM_MONTHS){
            return true;
        }
        return false;
    }

    public boolean isValidDay(int day, int month){
        if(day <= 29){
            return true;
        }
        if(day == 30 && (month == 11 || month == 4 || month == 6 || month == 9)){
            return true;
        }
        if(day == 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)){
            return true;
        }
        return false;
    }

    public boolean isValidQuantity(int quantity){
        if(quantity >= 0){
            return true;
        }
        return false;
    }
}