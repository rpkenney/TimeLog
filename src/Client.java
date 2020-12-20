import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Client{

    public static final String FILE_EXTENSION = ".txt";

    public static final String FILE_PATH = "clients/";

    public static final int CHARACTERS_PER_LINE = 50;

    private String name;

    private ArrayList<Entry> entries;

    private int numEntries;

    public Client(String name){
        this.name = name;

        entries = new ArrayList<Entry>();

        load();
    }

    public void addEntry(int day, int month, int year, double timeSpent, Category[] categories){
        Date date = new Date(day, month, year);
        entries.add(new Entry(date, categories, timeSpent));
        numEntries++;
        save();
    }

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
                    if(category.getQuantity() > 0){
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

    public void delete(){
        File file = new File(FILE_PATH + name + FILE_EXTENSION); 
        file.delete();
    }

    public String getName(){
        return name;
    }

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