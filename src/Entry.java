import java.util.ArrayList;

public class Entry{

    private Date date;

    private Client client;

    private ArrayList<Task> tasks;

    private int numTasks;

    private double timeSpent;

    public Entry(Client client, Date date, double timeSpent, ArrayList<Task> tasks){
        this.date = date;
        this.client = client;
        this.tasks = tasks;
        numTasks = tasks.toArray().length;
        this.timeSpent = timeSpent;
    }

    public String toString(){
        String str = new String();
        str += client.getName();
        str += "\n";
        str += date.toString(Date.SAVE);
        str += "\n";
        str += timeSpent;
        str += "\n";
        str += numTasks;
        str += "\n";
        for(int i = 0 ; i < numTasks; i++){
            str += tasks.get(i).toString();
        }
        return str;
    }
    public Date getDate(){
        return date;
    }

    public Client getClient(){
        return client;
    }

    public ArrayList<Task> getTasks(){
        return tasks;
    }

    public Task getTask(int task){
        return tasks.get(task);
    }

    public int getNumTasks(){
        return numTasks;
    }

    public double getTimeSpent(){
        return timeSpent;
    }
}