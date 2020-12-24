import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TimeLogger{

    public static final String FILE_PATH = "files/";

    public static final String FILE_EXTENSION = ".txt";

    public static final String CLIENTS_FILENAME = "clients";

    public static final String ENTRIES_FILENAME = "entries";

    public static final String CATEGORIES_FILENAME = "task_categories";

    public static final String PAY_FILENAME = "hourly_pay";

    public static final String CONTINUE_FLAG = "continue";

    public static final String END_FLAG = "end";

    private ArrayList<Entry> entries;

    private int numEntries;

    private ArrayList<Client> clients;

    private int numClients;

    private ArrayList<String> taskCategories;

    private int numTaskCategories;

    private double hourlyPay;

    private Scanner scnr;

    private PrintWriter out;

    public TimeLogger(){
        entries = new ArrayList<Entry>();
        clients = new ArrayList<Client>();
        taskCategories = new ArrayList<String>();

        loadEntries();
        loadClients();
        loadTaskCategories();
        loadHourlyPay();
    }

    public String getClientBreakdown(int month, int year){
        String str = "";
        Date today = new Date(getCurrentDay(), getCurrentMonth(), getCurrentYear());
        str += "Date Submitted: ";
        str += today.toMonthDayYear();
        str += "\n";
        str += "Total Hours: ";
        str += getTimeSpent(month, year);
        str += "\n";
        str += "Hourly Rate: $";
        str += String.format("%.2f", hourlyPay);
        str += "\n";
        str += "Total Pay: $";
        str += String.format("%.2f", getPay(month, year));
        str += "\n\n";
        ClientBreakdown cbd;
        for(int c = 0; c < numClients; c++){
            Client client = clients.get(c);
            cbd = new ClientBreakdown(client, month, year, entries, taskCategories);
            if(cbd.hasData()){
                str += cbd.getBreakdown();
                str += "\n";
            }
        }
        return str;
    }

    public String getDateBreakdown(int month, int year){
        String str = "";
        str += "Month of ";
        str += month;
        str += "/";
        str += year;
        str += "\n\n";
        DateBreakdown db;
        for(int day = 1; day <= Date.DAYS_IN_MONTH; day++){
            db = new DateBreakdown(new Date(day, month, year), entries, taskCategories);
            if(db.hasData()){
                str += db.getBreakdown();
                str += "\n";
            }
        }
        return str;
    }

    public String getYearlyBreakdown(int year){
        String str = "";
        str += "Year of ";
        str += year;
        str += "\n\n";
        
        for(int month = 1; month <= Date.NUM_MONTHS; month++){
            str += Date.MONTH_NAMES[month];
            str += ": $";
            str += String.format("%.2f", getPay(month, year));
            str += "\n";
        }
        str += "\n";
        str += "Total: $";
        str += String.format("%.2f", getPay(year));
        return str;
    }

    public void addEntry(Client client, Date date, ArrayList<Task> tasks, double timeSpent){
        entries.add(new Entry(client, date, timeSpent, tasks));
        numEntries++;
    }

    public void addTaskCategory(String category){
        taskCategories.add(category);
        numTaskCategories++;
    }

    public void addClient(String name){
        clients.add(new Client(name));
        numClients++;
    }

    public void removeEntry(int entry){
        entries.remove(entry);
        numEntries--;
    }

    public void removeClient(int client){
        clients.remove(client);
        numClients--;
    }

    public void removeTaskCategory(int category){
        taskCategories.remove(category);
        numTaskCategories--;
    }

    public void saveEntries(){
        String file = FILE_PATH + ENTRIES_FILENAME + FILE_EXTENSION;
        try{
            out = new PrintWriter(file);
        } catch(IOException e){
            System.out.println("Problem creating file: " + file);
            System.exit(1);
        }
        for(int i = 0; i < numEntries; i++){
            Entry entry = entries.get(i);
            out.println(entry.toString());
        }
        out.close();
    }

    public void saveClients(){
        String file = FILE_PATH + CLIENTS_FILENAME + FILE_EXTENSION;
        try{
            out = new PrintWriter(file);
        } catch(IOException e){
            System.out.println("Problem creating file: " + file);
            System.exit(1);
        }

        for(int i = 0; i < numClients; i++){
            out.println(clients.get(i).getName());
        }
        out.close();
    }

    public void saveTaskCategories(){
        String file = FILE_PATH + CATEGORIES_FILENAME + FILE_EXTENSION;
        try{
            out = new PrintWriter(file);
        } catch(IOException e){
            System.out.println("Problem creating file: " + file);
            System.exit(1);
        }

        for(int i = 0; i < numTaskCategories; i++){
            out.println(taskCategories.get(i));
        }
        out.close();
    }

    public void saveHourlyPay(){
        String file = FILE_PATH + PAY_FILENAME + FILE_EXTENSION;
        try{
            out = new PrintWriter(file);
        } catch(IOException e){
            System.out.println("Problem creating file: " + file);
            System.exit(1);
        }

        out.println(hourlyPay);
        out.close();
    }

    public void loadEntries(){
        String file = FILE_PATH + ENTRIES_FILENAME + FILE_EXTENSION;
        
        try{
            scnr = new Scanner(new FileInputStream(file));
        } catch(FileNotFoundException e){
            System.out.println("Problem loading file: " + file);
            System.exit(1);
        }

        while(scnr.hasNextLine()){
            Client client = new Client(scnr.nextLine());
            int day = scnr.nextInt();
            int month = scnr.nextInt();
            int year = scnr.nextInt();
            Date date = new Date(day, month, year);
            double timeSpent = scnr.nextDouble();
            int numTasks = scnr.nextInt();
            ArrayList<Task> tasks = new ArrayList<Task>();
            for(int i = 0; i < numTasks; i++){
                int quantity = scnr.nextInt();
                scnr.nextLine();
                String category = scnr.nextLine();
                String description = scnr.nextLine();
                tasks.add(new Task(quantity, category, description));
            }
            entries.add(new Entry(client, date, timeSpent, tasks));
            numEntries++;
        }
    }
    
    public void loadClients(){
        String file = FILE_PATH + CLIENTS_FILENAME + FILE_EXTENSION;
        
        try{
            scnr = new Scanner(new FileInputStream(file));
        } catch(FileNotFoundException e){
            System.out.println("Problem loading file: " + file);
            System.exit(1);
        }

        while(scnr.hasNextLine()){
            clients.add(new Client(scnr.nextLine()));
            numClients++;
        }
    }

    public void loadTaskCategories(){
        String file = FILE_PATH + CATEGORIES_FILENAME + FILE_EXTENSION;
        
        try{
            scnr = new Scanner(new FileInputStream(file));
        } catch(FileNotFoundException e){
            System.out.println("Problem loading file: " + file);
            System.exit(1);
        }

        while(scnr.hasNextLine()){
            taskCategories.add(scnr.nextLine());
            numTaskCategories++;
        }
    }

    public void loadHourlyPay(){
        String file = FILE_PATH + PAY_FILENAME + FILE_EXTENSION;
        
        try{
            scnr = new Scanner(new FileInputStream(file));
        } catch(FileNotFoundException e){
            System.out.println("Problem loading file: " + file);
        }
        hourlyPay = scnr.nextDouble();
    }

    public String[] getClientNames(){
        String[] clientNameList = new String[numClients];
        for(int client = 0; client < numClients; client++){
            clientNameList[client] = clients.get(client).getName();
        }
        return clientNameList;
    }

    public Client getClient(int client){
        return clients.get(client);
    }

    public String[] getTaskCategoryNames(){
        String[] taskCategoryNameList = new String[numTaskCategories];
        for(int taskCategory = 0; taskCategory < numTaskCategories; taskCategory++){
            taskCategoryNameList[taskCategory] = taskCategories.get(taskCategory);
        }
        return taskCategoryNameList;
    }

    public double getTimeSpent(Date date){
        double timeSpent = 0;
        for(int i = 0; i < numEntries; i++){
            Entry entry = entries.get(i);
            if(entry.getDate().equals(date)){
                timeSpent += entry.getTimeSpent();
            }
        }
        return timeSpent;
    }

    public double getTimeSpent(int month, int year){
        double timeSpent = 0;
        for(int i = 0; i < numEntries; i++){
            Entry entry = entries.get(i);
            if(entry.getDate().isWithin(month, year)){
                timeSpent += entry.getTimeSpent();
            }
        }
        return timeSpent;
    }

    public double getTimeSpent(int year){
        double timeSpent = 0;
        for(int i = 0; i < numEntries; i++){
            Entry entry = entries.get(i);
            if(entry.getDate().isWithin(year)){
                timeSpent += entry.getTimeSpent();
            }
        }
        return timeSpent;
    }

    public double getHourlyPay(){
        return hourlyPay;
    }

    public void setHourlyPay(double hourlyPay){
        this.hourlyPay = hourlyPay;
        saveHourlyPay();
    }

    public double getPay(int month, int year){
        return getTimeSpent(month, year) * hourlyPay;
    }

    public double getPay(int year){
        return getTimeSpent(year) * hourlyPay;
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

    public static void main(String[]args){
        TimeLogger tl = new TimeLogger();
    }
}