import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GetPayGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 400;

    public static final int HEIGHT = 300;

    private JTextField monthInput;

    private JTextField yearInput;

    private JLabel monthlyPay;

    private JLabel yearlyPay;

    private TimeLog timelog;

    public GetPayGUI(TimeLog timelog){
        this.timelog = timelog;

        JLabel title = new JLabel("Get Pay", SwingConstants.CENTER);
        title.setFont(TimeLogGUI.LARGE_FONT);

        JPanel inputPanel = new JPanel(new FlowLayout());

        JLabel monthLabel = new JLabel("Month:");
        monthInput = new JTextField(Integer.toString(timelog.getCurrentMonth()));
        JLabel yearLabel = new JLabel("Year:");
        yearInput = new JTextField(Integer.toString(timelog.getCurrentYear()));
        JButton button = new JButton("Calculate");
        button.addActionListener(this);

        inputPanel.add(monthLabel);
        inputPanel.add(monthInput);
        inputPanel.add(yearLabel);
        inputPanel.add(yearInput);
        inputPanel.add(button);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));

        yearlyPay = new JLabel("Yearly Pay: ");
        yearlyPay.setFont(TimeLogGUI.MEDIUM_FONT);
        centerPanel.add(yearlyPay, SwingConstants.CENTER);

        monthlyPay = new JLabel("Monthly Pay: ");
        monthlyPay.setFont(TimeLogGUI.MEDIUM_FONT);
        centerPanel.add(monthlyPay, SwingConstants.CENTER);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle("Get Pay");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

        if(timelog.isValidMonth(month) && timelog.isValidYear(year)){
            monthlyPay.setText("Monthly Pay: " + timelog.getPay(month, year));
            yearlyPay.setText("Yearly Pay: " + timelog.getPay(year));
        }
    }
}