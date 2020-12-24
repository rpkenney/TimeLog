import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReportGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 500;

    public static final int HEIGHT = 500;

    public static final int MONTHLY_SUMMARY = 0;

    public static final int MONTHLY_BREAKDOWN = 1;

    public static final int YEARLY_SUMMARY = 2;

    public static final Dimension REPORT_DIMENSION = new Dimension(400, 350);

    private TimeLogger timeLogger;

    private JTextArea report;

    private JButton button;

    private DateEntryPanel dateInput;

    private int format;

    public ReportGUI(String title, int format, TimeLogger timeLogger){
        this.timeLogger = timeLogger;
        this.format = format;

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(TimeLoggerGUI.TITLE_FONT);

        JPanel centerPanel = new JPanel(new FlowLayout());
        report = new JTextArea();
        JScrollPane reportScrollPane = new JScrollPane(report);
        reportScrollPane.setPreferredSize(REPORT_DIMENSION);
        centerPanel.add(reportScrollPane);

        JPanel inputPanel = new JPanel(new FlowLayout());

        dateInput = null;

        if(format == MONTHLY_SUMMARY || format == MONTHLY_BREAKDOWN){
            dateInput = new DateEntryPanel(DateEntryPanel.MONTH_YEAR, timeLogger);
        } else if(format == YEARLY_SUMMARY){
            dateInput = new DateEntryPanel(DateEntryPanel.YEAR, timeLogger);
        }
        inputPanel.add(dateInput);

        button = new JButton("Go");
        button.addActionListener(this);
        inputPanel.add(button);


        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(dateInput.isValidDate()){
            switch(format){
                case MONTHLY_SUMMARY:
                    report.setText(timeLogger.getClientBreakdown(dateInput.getMonth(), dateInput.getYear()));
                    break;
                case MONTHLY_BREAKDOWN:
                    report.setText(timeLogger.getDateBreakdown(dateInput.getMonth(), dateInput.getYear()));
                    break;
                case YEARLY_SUMMARY:
                    report.setText(timeLogger.getYearlyBreakdown(dateInput.getYear()));
                    break;
            }
        }
    }
}