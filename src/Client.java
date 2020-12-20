import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Clinet.java
 * 
 * This class creates an individual client, allows for the
 * loading and saving of old and new data, along
 * with the formatting of a client monthly report
 * 
 * @author Robert Kenney
 */
public class Client{

    /** the file extension for the client data files */
    public static final String FILE_EXTENSION = ".txt";

    /** the file path for the client data files */
    public static final String FILE_PATH = "clients/";

    /** the numbers of characters per line for the monthly report */
    public static final int CHARACTERS_PER_LINE = 50;

    /** the name of he client */
    private String name;

    /** an array list containing all of the entries that have been made */
    private ArrayList<Entry> entries;

    /** the number of entries for this client */
    private int numEntries;

    /**
     * This constructor initializes the client by assigning its name,
     * and loading any old entries for it from savefiles
     * @param name
     */
    public Client(String name){
        this.name = name;

        entries = new ArrayList<Entry>();

        load();
    }

    /**
     * This method adds an entry for the client, and saves it to memory
     * @param date the date of the entry
     * @param timeSpent the time spent on that date
     * @param categories the categories that were worked on for the client
     */
    public void addEntry(Date date, double timeSpent, Category[] categories){
        entries.add(new Entry(date, categories, timeSpent));
        numEntries++;
        save();
    }

    /**
     * this method generates and returns the monthly report for the client, 
     * consisting of total time spent, and quantities and descriptions for
     * each category of work that was done for the client
     * @param month the month of the report
     * @param year the year of the report
     * @return the report itself
     */
    public String getMonthlyReport(int month, int year){
        String report = "";
        if(numEntries > 0){
            report += name;
            report += "\n";
            report += getTimeSpent(month, year);
            report += " Hours\n";
        }
        report += getCategoriesString(month, year);
        return report;
    }

    /**
     * this method saves all of the clients current data
     * to memory
     */
    public void save(){
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream(FILE_PATH + name + FILE_EXTENSION));
        }
        catch (IOException e){
            System.out.println("Problem creating file");
            System.exit(1);
        }
        for(int i = 0; i < numEntries; i++){
            Entry entry = entries.get(i);
            out.print(entry.getDate().toString());
            out.println(" " + entry.getTimeSpent());
            for(int c = 0; c < Entry.NUM_CATEGORIES; c++){
                Category category = entry.getCategory(c);
                if(category != null && category.getDescription() != null){
                    if(category.hasQuantity()){
                        out.print(category.getQuantity() + " ");
                    }
                    out.println(category.getDescription());
                } else {
                    out.println();
                }
            }
        }
        out.close();
    }

    /**
     * this method loads all of the clients saved
     * memory, allowing for it to be incorported in monthly reports
     */
    public void load(){
        String file = FILE_PATH + name + FILE_EXTENSION;

        Path path = Path.of(file);
        if(Files.exists(path)){
            Scanner in = null;
            try{
                in = new Scanner(new FileInputStream(file));
            } catch(FileNotFoundException e){
                System.out.println("Client does not exist");
            }
            Scanner lineScan;
            String line;
            while(in.hasNextLine()){
                line = in.nextLine();
                if(line.isEmpty()){
                    continue;
                }
                lineScan = new Scanner(line);
                int day = lineScan.nextInt();
                int month = lineScan.nextInt();
                int year = lineScan.nextInt();
                Date date = new Date(day, month, year);
                double timeSpent = lineScan.nextDouble();

                Category[] categories = new Category[Entry.NUM_CATEGORIES];
                for(int i = 0; i < Entry.NUM_CATEGORIES; i++){
                    line = in.nextLine();
                    lineScan = new Scanner(line);
                    int quantity = 0;
                    String description;
                    if(lineScan.hasNextInt()){
                        quantity = lineScan.nextInt();
                        description = line.substring(2);
                    } else if(line.isEmpty()){
                        description = null;
                    } else {
                        description = line.substring(0);
                    }
                    categories[i] = new Category(Entry.CATEGORIES[i], description, quantity);
                }

                entries.add(new Entry(date, categories, timeSpent));
                numEntries++;
            }
        }
    }

    /**
     * this method allows for all data and about the client
     * that is saved, to be deleted
     */
    public void delete(){
        File file = new File(FILE_PATH + name + FILE_EXTENSION); 
        file.delete();
    }

    /**
     * returns the name of the client
     * @return the name of the client
     */
    public String getName(){
        return name;
    }

    /**
     * This method breaks down each of the categories of work that was
     * done for a client in a given month, and generates a comma seperated list
     * of each category and returns this information
     * @param month the month of the work
     * @param year the year of the work
     * @return the string containing all of the data
     */
    public String getCategoriesString(int month, int year){
        String str = "";
        String[] categoryDescriptions = new String[Entry.NUM_CATEGORIES];
        int[] quantities = new int[Entry.NUM_CATEGORIES];
        for(int i = 0; i < categoryDescriptions.length; i++){
            categoryDescriptions[i] = "";
        }
        for(int i = 0; i < numEntries; i++){
            Entry entry = entries.get(i);
            if(entry.isDateWithin(month, year)){
                for(int j = 0; j < Entry.NUM_CATEGORIES; j++){
                    Category category = entry.getCategory(j);
                    if(category != null && category.getDescription() != null){
                        categoryDescriptions[j] +=  splitIntoLines(category.getDescription());
                        if(i < numEntries - 1){
                            categoryDescriptions[j] += ", ";
                        }
                        quantities[j] += category.getQuantity();
                    }
                }
            }
        }
        for(int i = 0; i < Entry.NUM_CATEGORIES; i++){
            if(!categoryDescriptions[i].isEmpty()){
                if(quantities[i] > 0){
                    str += quantities[i];
                    str += " ";
                }
                str += Entry.CATEGORIES[i];
                str += ": ";
                str += categoryDescriptions[i];
                str += "\n";
            }
        }
        return str;
    }

    /**
     * This method splits up long strings into multiple lines,
     * ensuring that the report is in a format that is readable
     * @param str the string to be splitup
     * @return the string in multiple lines
     */
    public String splitIntoLines(String str){
        String lines = "";
        Scanner scnr = new Scanner(str);
        int numChars = 0;
        while(scnr.hasNext()){
            String word = scnr.next();
            lines += word;
            numChars += word.length();
            if(numChars > CHARACTERS_PER_LINE){
                numChars = 0;
                lines += "\n";
            } else if(scnr.hasNext()){
                lines += " ";
                numChars++;
            }
        }
        
        return lines;
    }

    /**
     * this method returns the total time spent working
     * for this client in a given month
     * @param month the month in question
     * @param year the year of that month
     * @return the total time spent on that client that month
     */
    public int getTimeSpent(int month, int year){
        int sum = 0;
        for(int i = 0; i < numEntries; i++){
            Entry entry = entries.get(i);
            if(entry.isDateWithin(month, year)){
                sum += entry.getTimeSpent();
            }
        }
        return sum;
    }

    /**
     * this method returns the total time spent working
     * for this client in a given year
     * @param year the year in question
     * @return the total time working for the client that year
     */
    public int getTimeSpent(int year){
        int sum = 0;
        for(int i = 0; i < numEntries; i++){
            Entry entry = entries.get(i);
            if(entry.isDateWithin(year)){
                sum += entry.getTimeSpent();
            }
        }
        return sum;
    }
}