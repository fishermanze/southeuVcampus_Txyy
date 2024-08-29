import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register {
    private JTextField textNameField;
    private JComboBox<String> identityComboBox;
    private JTextField emailField;
    private JTextField ageField;
    private JPasswordField textPasswordField;
    private JPasswordField confirmPasswordField;
    private JFrame registerFrame;
    private JTextField stuname;

    public void initUI() {
        registerFrame = new JFrame("注册界面");
        registerFrame.setSize(320,350);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭时只关闭注册窗口

        JPanel panel = new JPanel();
        registerFrame.add(panel);
        placeComponents(panel);

        registerFrame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("注册");
        titleLabel.setBounds(120, 1, 280, 30);
        panel.add(titleLabel);

        JLabel identityLabel = new JLabel("请选择注册身份：");
        identityLabel.setBounds(10, 30, 80, 25);
        panel.add(identityLabel);

        JComboBox<String> identityComboBox = new JComboBox<>();
        identityComboBox.addItem("学生");
        identityComboBox.addItem("管理员");
        identityComboBox.setBounds(100, 30, 165, 25);
        panel.add(identityComboBox);

        JLabel userLabel = new JLabel("一卡通号:");
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

        JLabel confirmPasswordLabel = new JLabel("确认密码:");
        confirmPasswordLabel.setBounds(10, 120, 80, 25);
        panel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBounds(100, 120, 165, 25);
        panel.add(confirmPasswordField);

        JLabel emailLabel = new JLabel("邮箱:");
        emailLabel.setBounds(10, 150, 80, 25);
        panel.add(emailLabel);

        JTextField emailField = new JTextField(20);
        emailField.setBounds(100, 150, 165, 25);
        panel.add(emailField);

        JLabel ageLabel = new JLabel("年龄");
        ageLabel.setBounds(10, 180, 80, 25);
        panel.add(ageLabel);

        JTextField ageField = new JTextField(20);
        ageField.setBounds(100, 180, 165, 25);
        panel.add(ageField);

        JLabel stunameLabel = new JLabel("姓名:");
        stunameLabel.setBounds(10, 210, 80, 25);
        panel.add(stunameLabel);

        stuname = new JTextField(20);
        stuname.setBounds(100, 210, 165, 25);
        panel.add(stuname);

        JButton confirmButton = new JButton("确定");
        confirmButton.setBounds(50, 260, 80, 25);
        panel.add(confirmButton);

        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(150, 260, 80, 25);
        panel.add(cancelButton);

        confirmButton.addActionListener(new registerListener(registerFrame, textNameField,stuname,textPasswordField, confirmPasswordField , emailField, ageField, identityComboBox));


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerFrame.dispose();
            }
        });
    }

    public JTextField getTextNameField() {
        return textNameField;
    }


    public JComboBox<String> getIdentityComboBox() {
        return identityComboBox;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getAgeField() {
        return ageField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JPasswordField getTextPasswordField() {
        return textPasswordField;
    }

    public JTextField getStuname() {
        return stuname;
    }

    public  static void main(String[] args) {
        new Register().initUI();
    }
}
