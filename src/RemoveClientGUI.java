import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RemoveClientGUI extends JFrame implements ActionListener{

    public static final int WIDTH = 400;

    public static final int HEIGHT = 300;

    public static final int CLIENT_LIST_WIDTH = 200;

    public static final int CLIENT_LIST_HEIGHT = 200;

    public static final int BUTTON_WIDTH = 100;

    public static final int BUTTON_HEIGHT = 40;

    private TimeLog timelog;

    private JList clients;

    private JScrollPane clientScrollPane;

    private JButton button;

    public RemoveClientGUI(TimeLog timelog){
        this.timelog = timelog;

        JLabel title = new JLabel("Remove a Client", SwingConstants.CENTER);
        title.setFont(TimeLogGUI.LARGE_FONT);

        JPanel centerPanel = new JPanel(new FlowLayout());
        clients = new JList(timelog.getClientNames());
        clientScrollPane = new JScrollPane(clients);
        clientScrollPane.setPreferredSize(new Dimension(CLIENT_LIST_WIDTH, CLIENT_LIST_HEIGHT));

        button = new JButton("Remove");
        button.addActionListener(this);

        centerPanel.add(clientScrollPane);
        centerPanel.add(button);

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setTitle("Remove Client");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(clients.getSelectedIndex() != -1){
            timelog.getClient(clients.getSelectedIndex()).delete();
            timelog.removeClient(clients.getSelectedIndex());
            dispose();
            RemoveClientGUI gui = new RemoveClientGUI(timelog);
        }
    }
}