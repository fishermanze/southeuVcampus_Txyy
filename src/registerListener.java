import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

// 监听事件
public class registerListener implements ActionListener {

    private JTextField usersname; // 账号输入框对象
    private JPasswordField password1;
    private JPasswordField password2;
    private JTextField emailadd;// 密码输入框对象
    private JFrame register; // 定义一个窗体对象
    private JComboBox<String> identityComboBox; // 身份选择框对象
    private JTextField agetext; // 登录界面对象
    private JTextField stuname; // 账号

    public registerListener(JFrame register, JTextField stuname, JTextField usersname, JPasswordField password1, JPasswordField password2, JTextField emailadd, JTextField agetext, JComboBox<String> identityComboBox) {
        this.register = register;// 获取登录界面
        this.usersname = usersname;
        this.stuname = stuname;
        this.password1 = password1;
        this.password2 = password2;
        this.emailadd = emailadd;
        this.agetext = agetext; // 获取登录界面对象
        this.identityComboBox = identityComboBox; // 获取身份选择框对象
    }

    public void actionPerformed(ActionEvent e) {
        try {
            Socket socket = ClientDemo.socket; // 获取全局Socket对象

            // 获取账号和密码,身份
            String username = usersname.getText();
            String studentname = stuname.getText();
            String password = new String(password1.getPassword());
            String repassword = new String(password2.getPassword());
            String email = emailadd.getText();
            int age = Integer.parseInt(agetext.getText());
            String identity = (String) identityComboBox.getSelectedItem();

            if (password.equals(repassword)) {
                OutputStream outputStream = socket.getOutputStream();
                String message = "REGISTER"+","+username + "," + studentname + "," + password + "," + identity + "," + age + "," + email;
                outputStream.write((message + "\n").getBytes()); // 确保消息以换行符结尾
                outputStream.flush();

                // 读取服务器的响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = reader.readLine();
                System.out.println("服务器响应: " + response);

                if ("注册成功".equals(response)) {
                    // 关闭注册窗口
                    register.dispose();

                    Login login = new Login();
                    login.initUI();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
