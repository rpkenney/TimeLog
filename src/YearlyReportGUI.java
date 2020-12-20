import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class YearlyReportGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 500;

    public static final int HEIGHT = 500;

    public static final Dimension REPORT_DIMENSION = new Dimension(400, 400);

    private TimeLog timelog;

    private JButton button;

    private JTextField yearInput;

    private JTextArea report;

    public YearlyReportGUI(TimeLog timelog){
        this.timelog = timelog;

        JLabel title = new JLabel("Yearly Report", SwingConstants.CENTER);
        title.setFont(TimeLogGUI.LARGE_FONT);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        
        yearInput = new JTextField(Integer.toString(timelog.getCurrentYear()));
        button = new JButton("Generate");
        button.addActionListener(this);


        JPanel yearPanel = new JPanel(new FlowLayout());
        yearPanel.add(new JLabel("Year:", SwingConstants.CENTER));
        yearPanel.add(yearInput);

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
        setTitle("Yearly Report");
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
        int year = 0;

        if(isValidInput(yearInput.getText())){
            year = Integer.parseInt(yearInput.getText());
        }

        if(!timelog.isValidYear(year)){
            JOptionPane.showMessageDialog(null,"Invalid Date");
        }

        if(timelog.isValidYear(year)){
            report.setText(timelog.generateYearlyReport(year));
        }
    }
}