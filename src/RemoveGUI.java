import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RemoveGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 500;

    public static final int HEIGHT = 500;

    public static final Dimension SCROLL_PANEL_DIMENSION = new Dimension(250, 300);

    public static final int CLIENT = 0;

    public static final int CATEGORY = 1;

    private JList list;

    private JButton button;

    private JLabel status;

    private TimeLogger timeLogger;

    private int toRemove;

    private String title;

    public RemoveGUI(String title, int toRemove, TimeLogger timeLogger){

        this.title = title;
        this.timeLogger = timeLogger;
        this.toRemove = toRemove;

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(TimeLoggerGUI.TITLE_FONT);

        JPanel centerPanel = new JPanel(new FlowLayout());
        if(toRemove == CLIENT){
            list = new JList(timeLogger.getClientNames());
        } else if(toRemove == CATEGORY){
            list = new JList(timeLogger.getTaskCategoryNames());
        }

        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setPreferredSize(SCROLL_PANEL_DIMENSION);

        centerPanel.add(listScrollPane);

        button = new JButton("Remove");
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

    public boolean isValidInput(){
        if(list.getSelectedIndex() != -1){
            return true;
        }
        return false;
    }

    public void actionPerformed(ActionEvent e){
        if(isValidInput()){
            if(toRemove == CLIENT){
                timeLogger.removeClient(list.getSelectedIndex());
                timeLogger.saveClients();
            } else if(toRemove == CATEGORY){
                timeLogger.removeTaskCategory(list.getSelectedIndex());
                timeLogger.saveTaskCategories();
            }
            RemoveGUI gui = new RemoveGUI(title, toRemove, timeLogger);
            this.dispose();
        } else {
            status.setText("Invalid Input");
        }
    }
}