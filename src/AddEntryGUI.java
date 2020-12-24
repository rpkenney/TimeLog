import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AddEntryGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 650;

    public static final int HEIGHT = 500;

    public static final int NUMERIC_INPUT_WIDTH = 2;

    public static final int NUM_PANELS = 3;

    private JTextField timeSpentInput;

    private DateEntryPanel dateInput;

    private TimeLogger timeLogger;

    private JButton logButton;

    private JList clientList;

    private JList categoryList;

    private JTextField quantityInput;

    private JTextArea descriptionInput;

    private JButton taskButton;

    private JButton cancelButton;

    private ArrayList<Task> tasks;

    private JLabel status;

    private int numTasks;
    
    public AddEntryGUI(TimeLogger timeLogger){

        this.timeLogger = timeLogger;

        tasks = new ArrayList<Task>();
        
        JLabel title = new JLabel("Add Data", SwingConstants.CENTER);
        title.setFont(TimeLoggerGUI.TITLE_FONT);

        JPanel centerPanel = new JPanel(new GridLayout(1, NUM_PANELS));

        clientList = new JList(timeLogger.getClientNames());
        JScrollPane clientScrollPane = new JScrollPane(clientList);
        centerPanel.add(clientScrollPane);

        categoryList = new JList(timeLogger.getTaskCategoryNames());
        JScrollPane categoryScrollPane = new JScrollPane(categoryList);
        centerPanel.add(categoryScrollPane);

        JPanel taskInputPanel = new JPanel(new GridLayout(5, 1));

        status = new JLabel("Add all tasks to queue before logging");
        taskInputPanel.add(status);

        JPanel quantityInputPanel = new JPanel(new FlowLayout());
        quantityInputPanel.add(new JLabel("Quantity:"));
        quantityInput = new JTextField("0");
        quantityInput.setColumns(NUMERIC_INPUT_WIDTH);
        quantityInputPanel.add(quantityInput);
        taskInputPanel.add(quantityInputPanel);

        JPanel descriptionInputPanel = new JPanel(new BorderLayout());
        descriptionInputPanel.add(new JLabel("Description:"), BorderLayout.NORTH);
        descriptionInput = new JTextArea();
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionInput);
        descriptionInputPanel.add(descriptionScrollPane, BorderLayout.CENTER);
        taskInputPanel.add(descriptionInputPanel);

        taskButton = new JButton("Add Task to Queue");
        taskButton.addActionListener(this);
        taskInputPanel.add(taskButton);

        cancelButton = new JButton("Cancel last Task");
        cancelButton.addActionListener(this);
        taskInputPanel.add(cancelButton);

        centerPanel.add(taskInputPanel);

        JPanel inputPanel = new JPanel(new FlowLayout());
        dateInput = new DateEntryPanel(DateEntryPanel.DAY_MONTH_YEAR, timeLogger);
        inputPanel.add(dateInput);
        
        inputPanel.add(new JLabel("Time Spent: "));
        timeSpentInput = new JTextField();
        timeSpentInput.setColumns(NUMERIC_INPUT_WIDTH);
        inputPanel.add(timeSpentInput);

        logButton = new JButton("Log");
        logButton.addActionListener(this);
        inputPanel.add(logButton);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle("Add Data");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public boolean isValidClient(){
        if(clientList.getSelectedIndex() != -1){
            return true;
        }
        return false;
    }

    public boolean isValidTime(){
        Scanner scnr = new Scanner(timeSpentInput.getText());

        if(scnr.hasNextDouble() && scnr.nextDouble() >= 0){
            return true;
        }
        return false;
    }

    public boolean isValidTask(){
        if(categoryList.getSelectedIndex() != -1){
            return true;
        }
        return false;
    }

    public boolean isValidQuantity(){
        Scanner scnr = new Scanner(quantityInput.getText());

        if(scnr.hasNextInt() && scnr.nextInt() >= 0){
            return true;
        }
        return false;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == taskButton){
            if(!isValidTask()){
                JOptionPane.showMessageDialog(null,"No Category Selected");
            } else if(!isValidQuantity()){
                JOptionPane.showMessageDialog(null,"Invalid Quantity");
            }

            if(isValidTask() && isValidClient()){
                String category = timeLogger.getTaskCategoryNames()[categoryList.getSelectedIndex()];
                tasks.add(new Task(Integer.parseInt(quantityInput.getText()), category, descriptionInput.getText()));
                numTasks++;
                status.setText(category + " Added. Task Count: " + numTasks);
            }
            quantityInput.setText("0");
            descriptionInput.setText("");
        }

        if(e.getSource() == logButton){
            if(!isValidClient()){
                JOptionPane.showMessageDialog(null,"No Client Selected");
            } else if(!isValidTime()){
                JOptionPane.showMessageDialog(null,"Invalid Time");
            } else if(!dateInput.isValidDate()){
                JOptionPane.showMessageDialog(null,"Invalid Date");
            }

            if(isValidClient() && isValidTime()){
                Client client = timeLogger.getClient(clientList.getSelectedIndex());
                timeLogger.addEntry(client, dateInput.getDate(), tasks, Double.parseDouble(timeSpentInput.getText()));
                timeLogger.saveEntries();
                tasks = new ArrayList<Task>();
                status.setText("Logged " + numTasks + " for " + client.getName());
                numTasks = 0;
            }
            timeSpentInput.setText("");
        }

        if(e.getSource() == cancelButton){
            if(numTasks > 0){
                numTasks--;
                String category = tasks.get(numTasks).getCategory();
                tasks.remove(numTasks);
                status.setText("Removed " + category + ". " + numTasks + "task(s) left");
            } else {
                JOptionPane.showMessageDialog(null,"No Tasks in Queue");
            }
        }
    }
    
}