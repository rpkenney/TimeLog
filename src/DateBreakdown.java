import java.util.ArrayList;

public class DateBreakdown{

    private Date date; 

    private ArrayList<Entry> entries;

    private int numEntries;

    private ArrayList<Client> clients;

    private int numClients;

    private ArrayList<String> taskCategories;

    public DateBreakdown(Date date, ArrayList<Entry> entries, ArrayList<String> taskCategories){
        this.date = date;
        this.taskCategories = taskCategories;

        this.entries = new ArrayList<Entry>();
        clients = new ArrayList<Client>();
        numEntries = 0;
        numClients = 0;
        for(int e = 0; e < entries.toArray().length; e++){
            if(entries.get(e).getDate().equals(date)){
                this.entries.add(entries.get(e));
                if(!clients.contains(entries.get(e).getClient())){
                    clients.add(entries.get(e).getClient());
                    numClients++;
                }
                numEntries++;
            }
        }
    }

    public String getBreakdown(){
        String str = "";
        str += date.toMonthDayYear();
        str += "\n";
        
        ClientBreakdown cbd;
        for(int c = 0; c < numClients; c++){
            cbd = new ClientBreakdown(clients.get(c), date, entries, taskCategories);
            str += cbd.getBreakdown();
            str += "\n";
        }
        return str;
    }

    public boolean hasData(){
        if(numEntries > 0){
            return true;
        }
        return false;
    }

}