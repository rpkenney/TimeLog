import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 400;

    public static final int HEIGHT = 200;

    public static final int TEXT_BOX_WIDTH = 30;

    public static final int CLIENT = 0;

    public static final int CATEGORY = 1;

    private TimeLogger timeLogger;

    private JTextField nameField;

    private JButton button;

    private JLabel status;

    private int toAdd;

    public AddGUI(String title, int toAdd, TimeLogger timeLogger){
        this.timeLogger = timeLogger;
        this.toAdd = toAdd;

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(TimeLoggerGUI.TITLE_FONT);


        JPanel centerPanel = new JPanel(new FlowLayout());

        centerPanel.add(new JLabel("Name: "));

        nameField = new JTextField();
        nameField.setColumns(TEXT_BOX_WIDTH);
        centerPanel.add(nameField);

        button = new JButton("Add");
        button.addActionListener(this);
        centerPanel.add(button);

        status = new JLabel("", SwingConstants.CENTER);


        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public boolean isValidInput(String input){
        if(!input.isBlank() && input != null){
            return true;
        }
        return false;
    }

    public void actionPerformed(ActionEvent e){

        if(isValidInput(nameField.getText())){
            if(toAdd == CLIENT){
                timeLogger.addClient(nameField.getText());
                timeLogger.saveClients();
            } else if(toAdd == CATEGORY){
                timeLogger.addTaskCategory(nameField.getText());
                timeLogger.saveTaskCategories();
            }
            status.setText("Created and Saved " + nameField.getText());
            nameField.setText("");
        } else {
            status.setText("Invalid Input!");
            nameField.setText("");
        }
    }
}