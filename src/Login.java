import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextField textNameField;
    private JPasswordField textPasswordField;
    private JFrame loginFrame;
    private JComboBox<String> identityComboBox;

    public void initUI() {
        loginFrame = new JFrame("登录界面");
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        loginFrame.add(panel);
        placeComponents(panel);

        loginFrame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("登录");
        titleLabel.setBounds(120, 1, 280, 30);
        panel.add(titleLabel);

        JLabel identityLabel = new JLabel("请选择身份：");
        identityLabel.setBounds(10, 30, 80, 25);
        panel.add(identityLabel);

        identityComboBox = new JComboBox<>();
        identityComboBox.addItem("学生");
        identityComboBox.addItem("管理员");
        identityComboBox.setBounds(100, 30, 165, 25);
        panel.add(identityComboBox);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(10, 60, 80, 25);
        panel.add(userLabel);

        textNameField = new JTextField(20);
        textNameField.setBounds(100, 60, 165, 25);
        panel.add(textNameField);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10, 90, 80, 25);
        panel.add(passwordLabel);

        textPasswordField = new JPasswordField(20);
        textPasswordField.setBounds(100, 90, 165, 25);
        panel.add(textPasswordField);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(50, 120, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(150, 120, 80, 25);
        panel.add(registerButton);

        loginButton.addActionListener(new LoginListener(loginFrame, textNameField, textPasswordField, identityComboBox));



        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                register.initUI();
                loginFrame.dispose();
            }
        });
    }

    public JTextField getTextNameField() {
        return textNameField;
    }

    public JPasswordField getTextPasswordField() {
        return textPasswordField;
    }

    public static void main(String[] args) {
        Login login = new Login();
        login.initUI();
    }
}
