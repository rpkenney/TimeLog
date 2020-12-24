import java.util.ArrayList;

public class ClientBreakdown{

    private double timeSpent;

    private Client client;

    private ArrayList<Entry> entries;

    private ArrayList<String> taskCatgeories;

    private int numEntries;

    public ClientBreakdown(Client client, int month, int year, ArrayList<Entry> entries, ArrayList<String> taskCategories){
        this.client = client;
        this.taskCatgeories = taskCategories;
        numEntries = 0;

        this.entries = new ArrayList<Entry>();
        timeSpent = 0;
        for(int e = 0; e < entries.toArray().length; e++){
            Entry entry = entries.get(e);
            if(entry.getClient().equals(client) && entry.getDate().isWithin(month, year)){
                timeSpent += entry.getTimeSpent();
                this.entries.add(entry);
                numEntries++;
            }
        }
    }

    public ClientBreakdown(Client client, Date date, ArrayList<Entry> entries, ArrayList<String> taskCategories){
        this.client = client;
        this.taskCatgeories = taskCategories;
        numEntries = 0;

        timeSpent = 0;
        this.entries = new ArrayList<Entry>();
        for(int e = 0; e < entries.toArray().length; e++){
            Entry entry = entries.get(e);
            if(entry.getClient().equals(client) && entry.getDate().equals(date)){
                this.entries.add(entry);
                timeSpent += entry.getTimeSpent();
                numEntries++;
            }
        }
    }

    public boolean hasData(){
        if(timeSpent > 0){
            return true;
        }
        return false;
    }

    public boolean categoryHasData(String description, int quantity){

        if(!description.isEmpty() && quantity > 0){
            return true;
        }
        return false;

    }
    public String getBreakdown(){
        String str = "";
        str += client.getName();
        str += "\n";
        str += "Hours: ";
        str += timeSpent;
        str += "\n";
        
        for(int c = 0; c < taskCatgeories.toArray().length; c++){
            
            if(getCategoryString(taskCatgeories.get(c)) != null){
                str += getCategoryString(taskCatgeories.get(c));
                str += "\n";
            }
        }
        return str;
    }

    public String getCategoryString(String category){
        String str = "";
        int quantity = 0;
        for(int e = 0; e < numEntries; e++){
            Entry entry = entries.get(e);
            for(int t = 0; t < entry.getNumTasks(); t++){
                Task task = entry.getTask(t);
                
                if(task.getCategory().equals(category)){
                    quantity += task.getQuantity();
                }
            }
        }
        if(quantity > 0){
            str += quantity;
            str += " ";
        }
        str += category;
        str += ": ";

        int numDescriptions = 0;
        for(int e = 0; e < numEntries; e++){
            Entry entry = entries.get(e);
            for(int t = 0; t < entry.getNumTasks(); t++){
                Task task = entry.getTask(t);
                
                if(task.getCategory().equals(category)){
                    if(!task.getDescription().isEmpty()){
                        if(!task.getDescription().isBlank()){
                            numDescriptions++;
                        }
                    }
                }
            }
        }

        String[] descriptions = new String[numDescriptions];
        int d = 0;
        for(int e = 0; e < numEntries; e++){
            Entry entry = entries.get(e);
            for(int t = 0; t < entry.getNumTasks(); t++){
                Task task = entry.getTask(t);
                
                if(task.getCategory().equals(category)){
                    if(!task.getDescription().isEmpty()){
                        descriptions[d] = task.getDescription();
                        d++;
                    }
                }
            }
        }

        for(d = 0; d < numDescriptions; d++){
            str += descriptions[d];
            if(d < numDescriptions - 1){
                str += ", ";
            }
        }
        if(quantity == 0 && numDescriptions == 0){
            return null;
        }
        return str;
    }
}