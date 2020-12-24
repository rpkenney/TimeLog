import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimeLoggerGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 600;

    public static final int HEIGHT = 600;

    public static final int NUM_OPTIONS = 10;

    public static final Font TITLE_FONT = new Font("TimesRoman", Font.BOLD, 30);

    public static final Font BUTTON_FONT = new Font("TimesRoman", Font.PLAIN, 20);

    private JButton addEntryButton;

    private JButton monthlySummaryButton;

    private JButton monthlyBreakdownButton;

    private JButton yearlySummaryButton;

    private JButton adjustHourlyWageButton;

    private JButton addClientButton;
    
    private JButton removeClientButton;

    private JButton addTaskCategoryButton;

    private JButton removeTaskCategoryButton;

    private TimeLogger timeLogger;

    public TimeLoggerGUI(){
        timeLogger = new TimeLogger();

        JLabel title = new JLabel("Time Logger", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);

        JPanel centerPanel = new JPanel(new GridLayout(NUM_OPTIONS / 2, 2));

        addEntryButton = new JButton("Add Entry");
        addEntryButton.addActionListener(this);
        addEntryButton.setFont(BUTTON_FONT);

        monthlySummaryButton = new JButton("Monthly Summary");
        monthlySummaryButton.addActionListener(this);
        monthlySummaryButton.setFont(BUTTON_FONT);

        monthlyBreakdownButton = new JButton("Monthly Breakdown");
        monthlyBreakdownButton.addActionListener(this);
        monthlyBreakdownButton.setFont(BUTTON_FONT);

        yearlySummaryButton = new JButton("Yearly Summary");
        yearlySummaryButton.addActionListener(this);
        yearlySummaryButton.setFont(BUTTON_FONT);

        adjustHourlyWageButton = new JButton("Change Hourly Wage");
        adjustHourlyWageButton.addActionListener(this);
        adjustHourlyWageButton.setFont(BUTTON_FONT);

        addClientButton = new JButton("Add Client");
        addClientButton.addActionListener(this);
        addClientButton.setFont(BUTTON_FONT);

        removeClientButton = new JButton("Remove Client");
        removeClientButton.addActionListener(this);
        removeClientButton.setFont(BUTTON_FONT);

        addTaskCategoryButton = new JButton("Add Task Category");
        addTaskCategoryButton.addActionListener(this);
        addTaskCategoryButton.setFont(BUTTON_FONT);

        removeTaskCategoryButton = new JButton("Remove Task Category");
        removeTaskCategoryButton.addActionListener(this);
        removeTaskCategoryButton.setFont(BUTTON_FONT);

        centerPanel.add(addEntryButton);
        centerPanel.add(monthlySummaryButton);
        centerPanel.add(monthlyBreakdownButton);
        centerPanel.add(yearlySummaryButton);
        centerPanel.add(addClientButton);
        centerPanel.add(removeClientButton);
        centerPanel.add(addTaskCategoryButton);
        centerPanel.add(removeTaskCategoryButton);
        centerPanel.add(adjustHourlyWageButton);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setTitle("Time Logger");

    }

    public void actionPerformed(ActionEvent e){
        
        if(e.getSource() == addEntryButton){
            AddEntryGUI addEntryGUI = new AddEntryGUI(timeLogger);
        } else if(e.getSource() == monthlySummaryButton){
            ReportGUI monthlySummaryGUI = new ReportGUI("Monthly Summary", ReportGUI.MONTHLY_SUMMARY, timeLogger);
        } else if(e.getSource() == monthlyBreakdownButton){
            ReportGUI monthlyBreakdownGUI = new ReportGUI("Monthly Breakdown", ReportGUI.MONTHLY_BREAKDOWN, timeLogger);
        } else if(e.getSource() == yearlySummaryButton){
            ReportGUI monthlyBreakdownGUI = new ReportGUI("Yearly Summary", ReportGUI.YEARLY_SUMMARY, timeLogger);
        } else if(e.getSource() == adjustHourlyWageButton){
            ChangeHourlyRateGUI changeHourlyRateGUI = new ChangeHourlyRateGUI(timeLogger);
        } else if(e.getSource() == addClientButton){
            AddGUI addClientGUI = new AddGUI("Add New Client", AddGUI.CLIENT, timeLogger);

        } else if(e.getSource() == removeClientButton){
            RemoveGUI removeClientGUI = new RemoveGUI("Remove Client", RemoveGUI.CLIENT, timeLogger);
        } else if(e.getSource() == addTaskCategoryButton){
            AddGUI addTaskCategoryGUI = new AddGUI("Add New Task Category", AddGUI.CATEGORY, timeLogger);
        } else if(e.getSource() == removeTaskCategoryButton){
            RemoveGUI removeClientGUI = new RemoveGUI("Remove Category", RemoveGUI.CATEGORY, timeLogger);
        }
    }

    public static void main(String[]args){
        TimeLoggerGUI gui = new TimeLoggerGUI();
    }
}