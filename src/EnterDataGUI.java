import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EnterDataGUI extends JFrame implements ActionListener, MouseListener{ 

    public static final int WIDTH = 650;

    public static final int HEIGHT = 500;

    public static final Dimension CLIENT_LIST_DIMENSION = new Dimension(300, 300);

    public static final Dimension CATEGORY_LIST_DIMENSION = new Dimension(125, 300);

    public static final Dimension CATEGORY_INPUT_DIMENSION = new Dimension(150, 300);

    public static final int NUMERIC_INPUT_WIDTH = 2;

    private TimeLog timelog;

    private JTextField dayInput;

    private JTextField monthInput;

    private JTextField yearInput;

    private JTextField timeInput;

    private JButton addButton;

    private JButton logButton;

    private JList clients;

    private JList categoryNames;

    private JTextField quantityInput;

    private JTextArea descriptionInput;

    private JLabel status;

    private JLabel queue;

    private Category[] categories;

    public EnterDataGUI(TimeLog timelog){
        this.timelog = timelog;
        categories = new Category[Entry.NUM_CATEGORIES];

        JLabel title = new JLabel("Enter Data", SwingConstants.CENTER);
        title.setFont(TimeLogGUI.LARGE_FONT);

        clients = new JList(timelog.getClientNames());
        JScrollPane clientScrollPane = new JScrollPane(clients);
        clientScrollPane.setPreferredSize(CLIENT_LIST_DIMENSION);

        categoryNames = new JList(Entry.CATEGORIES);
        categoryNames.addMouseListener(this);
        JScrollPane categoryScrollPane = new JScrollPane(categoryNames);
        categoryScrollPane.setPreferredSize(CATEGORY_LIST_DIMENSION);
    
        JPanel quantityPanel = new JPanel(new FlowLayout());
        quantityInput = new JTextField("0");
        quantityInput.setColumns(NUMERIC_INPUT_WIDTH);
        quantityPanel.add(new JLabel("Quantity: "));
        quantityPanel.add(quantityInput);

        descriptionInput = new JTextArea();
        JScrollPane descriptionInputScrollPanel = new JScrollPane(descriptionInput);

        status = new JLabel();

        addButton = new JButton("Add Category");
        addButton.addActionListener(this);

        JPanel categoryInputPanel = new JPanel(new GridLayout(5, 1));
        categoryInputPanel.setPreferredSize(CATEGORY_INPUT_DIMENSION);
        categoryInputPanel.add(status);
        categoryInputPanel.add(new JLabel("Description", SwingConstants.CENTER));
        categoryInputPanel.add(descriptionInputScrollPanel);
        categoryInputPanel.add(quantityPanel);
        categoryInputPanel.add(addButton);

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(clientScrollPane);
        centerPanel.add(categoryScrollPane);
        centerPanel.add(categoryInputPanel);

        JPanel inputPanel = new JPanel(new FlowLayout());
        
        JLabel dayLabel = new JLabel("Day:");
        dayInput = new JTextField(Integer.toString(timelog.getCurrentDay()));
        JLabel monthLabel = new JLabel("Month:");
        monthInput = new JTextField(Integer.toString(timelog.getCurrentMonth()));
        JLabel yearLabel = new JLabel("Year:");
        yearInput = new JTextField(Integer.toString(timelog.getCurrentYear()));
        JLabel timeLabel = new JLabel("Time Spent");
        timeInput = new JTextField();
        timeInput.setColumns(NUMERIC_INPUT_WIDTH);

        queue = new JLabel("(0 activities in queue)");

        logButton = new JButton("Log Client");
        logButton.addActionListener(this);
        
        inputPanel.add(dayLabel);
        inputPanel.add(dayInput);
        inputPanel.add(monthLabel);
        inputPanel.add(monthInput);
        inputPanel.add(yearLabel);
        inputPanel.add(yearInput);
        inputPanel.add(timeLabel);
        inputPanel.add(timeInput);
        inputPanel.add(logButton);
        inputPanel.add(queue);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle("Data Entry");
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

    public boolean isValidDouble(String input){
        Scanner scnr = new Scanner(input);
        if(scnr.hasNextDouble()){
            return true;
        }
        return false;
    }

    public void actionPerformed(ActionEvent e){
        int client = clients.getSelectedIndex();
        int category = categoryNames.getSelectedIndex();
        String selectedCategory = "";
        String selectedClient = "";
        int day = 0;
        int month = 0;
        int year = 0;
        double timeSpent = 0;
        String description = descriptionInput.getText();
        int quantity = 0;

        if(isValidInput(dayInput.getText())){
            day = Integer.parseInt(dayInput.getText());
        }
        if(isValidInput(monthInput.getText())){
            month = Integer.parseInt(monthInput.getText());
        }
        if(isValidInput(yearInput.getText())){
            year = Integer.parseInt(yearInput.getText());
        }
        if(isValidDouble(timeInput.getText())){
            timeSpent = Double.parseDouble(timeInput.getText());
        }
        if(isValidInput(quantityInput.getText())){
            quantity = Integer.parseInt(quantityInput.getText());
        }

        if(e.getSource() == addButton){
            if(category == -1){
                JOptionPane.showMessageDialog(null,"No Category Selected");
            } else if(!timelog.isValidQuantity(quantity)){
                JOptionPane.showMessageDialog(null,"Invalid Quantity");
            }
            if(timelog.isValidQuantity(quantity) && category != -1){
                int numEntries = 0;
                categories[category] = new Category(Entry.CATEGORIES[category], description, quantity);
                status.setText("Added " + selectedCategory + " to queue");
                descriptionInput.setText("");
                quantityInput.setText("0");
                for(int i = 0 ; i < categories.length; i++){
                    if(categories[i] != null){
                        numEntries++;
                    }
                }
                queue.setText("(" + numEntries + " activities in queue" + ")");
            }
        }

        if(e.getSource() == logButton){
            if(clients.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(null,"No Client Selected");
            } else if(!timelog.isValidTime(timeSpent)){
                JOptionPane.showMessageDialog(null,"Invalid Time");
            } else if(!timelog.isValidMonth(month) || !timelog.isValidDay(day, month) || !timelog.isValidYear(year)){
                JOptionPane.showMessageDialog(null,"Invalid Date");
            }
            if(client != -1 && timelog.isValidDay(day, month) && timelog.isValidMonth(month) && timelog.isValidYear(year) && timelog.isValidTime(timeSpent)){
                selectedCategory = Entry.CATEGORIES[category];
                selectedClient = timelog.getClientNames()[client];
                Date date = new Date(day, month, year);
                timelog.getClient(client).addEntry(date, timeSpent, categories);
                status.setText("Logged for " + selectedClient);
                categories = new Category[Entry.NUM_CATEGORIES];
                queue.setText("(0 activities in queue)");
                descriptionInput.setText("");
                quantityInput.setText("0");
                timeInput.setText("");
            }
        }

    }

    public void mouseClicked(MouseEvent e){
        int category = categoryNames.getSelectedIndex();
        String selectedCategory = Entry.CATEGORIES[category];
        status.setText("Describing " + selectedCategory);
        if(categories[category] != null){
            descriptionInput.setText(categories[category].getDescription());
        } else if(categories[category] != null && !categories[category].getDescription().isEmpty()){
            descriptionInput.setText("");
        }
        quantityInput.setText("0");
    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){

    }
    public void mousePressed(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }
}