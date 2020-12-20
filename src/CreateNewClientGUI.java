import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateNewClientGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 400;

    public static final int HEIGHT = 200;

    public static final int TEXT_BOX_WIDTH = 30;

    public static final int BUTTON_WIDTH = 100;

    public static final int BUTTON_HEIGHT = 40;

    private TimeLog timelog;

    private JPanel centerPanel;

    private JTextField nameField;

    private JButton button;

    public CreateNewClientGUI(TimeLog timelog){
        this.timelog = timelog;

        JLabel title = new JLabel("Create a New Client", SwingConstants.CENTER);
        title.setFont(TimeLogGUI.LARGE_FONT);

        JPanel centerPanel = new JPanel(new FlowLayout());

        JLabel label = new JLabel("Client Name:", SwingConstants.CENTER);

        nameField = new JTextField();
        nameField.setColumns(TEXT_BOX_WIDTH);

        button = new JButton("Create");
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.addActionListener(this);

        centerPanel.add(label);
        centerPanel.add(nameField);
        centerPanel.add(button);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle("Create a New Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(nameField.getText() != null){
            if(isValidInput(nameField.getText())){
                timelog.addClient(nameField.getText());
                timelog.getClient(nameField.getText()).save();
                nameField.setText("");
            }
        }
    }

    public boolean isValidInput(String input){
        if(!input.isBlank() && input != null){
            return true;
        }
        return false;
    }
}