import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * ColorPicker.java
 * 
 * This class creates a GUI for the Time Log
 * 
 * @author Robert Kenney
*/
public class TimeLogGUI extends JFrame implements ActionListener{

    /** The width of the GUI */
    public static final int WIDTH = 500;

    /** The height of the GUI */
    public static final int HEIGHT = 500;

    /** The largest font used in the gui */
    public static final Font LARGE_FONT = new Font("TimesRoman", Font.BOLD, 30);

    /** The medium font used in the gui */
    public static final Font MEDIUM_FONT = new Font("TimesRoman", Font.PLAIN, 20);

    /** The smallest font used in the gui */
    public static final Font SMALL_FONT = new Font("TimesRoman", Font.PLAIN, 10);

    public static final int NUM_OPTION_BUTTONS = 6;

    private TimeLog timelog;

    private JButton enterData;

    private JButton monthlyReport;

    private JButton yearlyReport;

    private JButton getPay;

    private JButton createNewClient;

    private JButton removeClient;

    /**
     * This is the constructor which sets up and initializes the GUI
    */
    public TimeLogGUI(){
        timelog = new TimeLog();

        JLabel title = new JLabel("Time Log", SwingConstants.CENTER);
        title.setFont(LARGE_FONT);

        enterData = new JButton("Enter Data");
        enterData.setFont(MEDIUM_FONT);
        enterData.addActionListener(this);

        monthlyReport = new JButton("Monthly Report");
        monthlyReport.setFont(MEDIUM_FONT);
        monthlyReport.addActionListener(this);

        yearlyReport = new JButton("Yearly Report");
        yearlyReport.setFont(MEDIUM_FONT);
        yearlyReport.addActionListener(this);

        getPay = new JButton("Get Pay");
        getPay.setFont(MEDIUM_FONT);
        getPay.addActionListener(this);

        createNewClient = new JButton("Create a New Client");
        createNewClient.setFont(MEDIUM_FONT);
        createNewClient.addActionListener(this);

        removeClient = new JButton("Remove a Client");
        removeClient.setFont(MEDIUM_FONT);
        removeClient.addActionListener(this);

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel = new JPanel(new GridLayout(NUM_OPTION_BUTTONS / 2, 2));
        
        centerPanel.add(enterData);
        centerPanel.add(monthlyReport);
        centerPanel.add(yearlyReport);
        centerPanel.add(getPay);
        centerPanel.add(createNewClient);
        centerPanel.add(removeClient);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(HEIGHT, WIDTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Time Log");
        setVisible(true);
    }

    /**
     * This method handles button presses
     * @param e the ActionEvent
    */
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == enterData){
            EnterDataGUI gui = new EnterDataGUI(timelog);
        }
        
        if(e.getSource() == monthlyReport){
            MonthlyReportGUI gui = new MonthlyReportGUI(timelog);
        }

        if(e.getSource() == getPay){
            GetPayGUI gui = new GetPayGUI(timelog);
        }

        if(e.getSource() == yearlyReport){
            YearlyReportGUI gui = new YearlyReportGUI(timelog);
        }

        if(e.getSource() == createNewClient){
            CreateNewClientGUI gui = new CreateNewClientGUI(timelog);
        }

        if(e.getSource() == removeClient){
            RemoveClientGUI gui = new RemoveClientGUI(timelog);
        }
    }

    /**
     * This is the main method, which creates the TimeLogGUI
     * 
     * @param args Command line arguments, not used
    */
    public static void main(String[]args){
        TimeLogGUI gui = new TimeLogGUI();
    }
}