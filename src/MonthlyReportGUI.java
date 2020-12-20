import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MonthlyReportGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 500;

    public static final int HEIGHT = 500;

    public static final Dimension REPORT_DIMENSION = new Dimension(400, 350);

    private TimeLog timelog;

    private JButton button;

    private JTextField monthInput;

    private JTextField yearInput;

    private JTextArea report;

    public MonthlyReportGUI(TimeLog timelog){
        this.timelog = timelog;

        JLabel title = new JLabel("Monthly Report", SwingConstants.CENTER);
        title.setFont(TimeLogGUI.LARGE_FONT);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        
        monthInput = new JTextField(Integer.toString(timelog.getCurrentMonth()));
        yearInput = new JTextField(Integer.toString(timelog.getCurrentYear()));
        button = new JButton("Generate");
        button.addActionListener(this);


        JPanel monthPanel = new JPanel(new FlowLayout());
        monthPanel.add(new JLabel("Month:", SwingConstants.CENTER));
        monthPanel.add(monthInput);
        JPanel yearPanel = new JPanel(new FlowLayout());
        yearPanel.add(new JLabel("Year:", SwingConstants.CENTER));
        yearPanel.add(yearInput);

        bottomPanel.add(monthPanel);
        bottomPanel.add(yearPanel);
        bottomPanel.add(button);

        JPanel centerPanel = new JPanel(new FlowLayout());
        report = new JTextArea();
        JScrollPane reportScrollPane = new JScrollPane(report);
        reportScrollPane.setPreferredSize(REPORT_DIMENSION);
        centerPanel.add(reportScrollPane);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);



        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Monthly Report");
        setVisible(true);
    }

    public boolean isValidInput(String input){
        Scanner scnr = new Scanner(input);
        if(scnr.hasNextInt()){
            return true;
        }
        return false;
    } 

    public void actionPerformed(ActionEvent e){
        int month = 0;
        int year = 0;

        if(isValidInput(monthInput.getText())){
            month = Integer.parseInt(monthInput.getText());
        }
        if(isValidInput(yearInput.getText())){
            year = Integer.parseInt(yearInput.getText());
        }

        if(!timelog.isValidMonth(month) || !timelog.isValidYear(year)){
            JOptionPane.showMessageDialog(null,"Invalid Date");
        }
        if(timelog.isValidMonth(month) && timelog.isValidYear(year)){
            report.setText(timelog.generateMonthlyReport(month, year));
        }
    }
}