import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    private JPanel contentPanel;

    public HomePage() {
        // Set the title of the frame
        setTitle("智能校园");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel with a BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Create a panel for the buttons on the right
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10)); // 6 rows, 1 column, spacing 10px
        buttonPanel.setPreferredSize(new Dimension(100, 400)); // Set preferred size for the button panel

        // Add buttons to the button panel
        JButton button1 = new JButton("个人信息");
        buttonPanel.add(button1);
        JButton button2 = new JButton("选课系统");
        buttonPanel.add(button2);
        JButton button3 = new JButton("图书馆");
        buttonPanel.add(button3);
        JButton button4 = new JButton("商店");
        buttonPanel.add(button4);
        JButton button5 = new JButton("课程表");
        buttonPanel.add(button5);
        JButton button6 = new JButton("学籍管理");
        buttonPanel.add(button6);

        // Create the exit button separately and add it below the other buttons
        JButton exitButton = new JButton("退出");
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitPanel.add(exitButton);

        // Add the button panel and the exit button panel to the main panel (right side)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(buttonPanel, BorderLayout.CENTER);
        rightPanel.add(exitPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Create a panel for the content on the left
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(400, 400)); // Set preferred size for the content panel
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border to the content panel
        JLabel defaultLabel = new JLabel("此框插入变化的页面", SwingConstants.CENTER);
        contentPanel.add(defaultLabel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Create a text area for the bottom section
        JTextArea textArea = new JTextArea("Text");
        textArea.setEditable(false); // Set text area as non-editable
        mainPanel.add(textArea, BorderLayout.SOUTH);

        // Pack the frame and set it visible
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        exitButton.addActionListener(e -> System.exit(0)); // Add an action listener to the exit button

        // Add action listeners for the buttons
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPersonInfoPage();
            }
        });

        // Add other action listeners for other buttons as needed
    }

    private void showPersonInfoPage() {
        Personinfo personinfo = new Personinfo();
        contentPanel.removeAll();
        contentPanel.add(personinfo.getPersonInfoPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

    }

    public static void main(String[] args) {
        new HomePage();
    }
}
