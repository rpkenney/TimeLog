import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChangeHourlyRateGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 500;

    public static final int HEIGHT = 200;

    public static final int INPUT_WIDTH = 4;

    private JTextField input;

    private JButton button;

    private JLabel status;

    private TimeLogger timeLogger;

    public ChangeHourlyRateGUI(TimeLogger timeLogger){
        this.timeLogger = timeLogger;

        JLabel title = new JLabel("Change Hourly Rate", SwingConstants.CENTER);
        title.setFont(TimeLoggerGUI.TITLE_FONT);

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(new JLabel("Hourly Rate: $"));

        input = new JTextField();
        input.setColumns(INPUT_WIDTH);
        centerPanel.add(input);

        button = new JButton("Change");
        button.addActionListener(this);
        centerPanel.add(button);

        status = new JLabel("Current Hourly Rate: " + String.format("%.2f", timeLogger.getHourlyPay()), SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle("Change Hourly Rate");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public boolean isValidInput(){
        if(getRate() > 0){
            return true;
        }

        return false;
    }

    public double getRate(){
        double rate = 0;
        Scanner scnr = new Scanner(input.getText());
        if(scnr.hasNextDouble()){
            rate = scnr.nextDouble();
        }
        return rate;
    }

    public void actionPerformed(ActionEvent e){
        if(isValidInput()){
            timeLogger.setHourlyPay(getRate());
            status.setText("Updated hourly rate to: $" + String.format("%.2f", getRate()));
            input.setText("");
        } else {
            input.setText("");
            status.setText("Invalid Input");
        }
        
    }
}