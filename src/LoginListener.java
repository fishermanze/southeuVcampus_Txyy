import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

// 监听事件
public class LoginListener implements ActionListener {

    private javax.swing.JTextField jt; // 账号输入框对象
    private javax.swing.JPasswordField jp; // 密码输入框对象
    private javax.swing.JFrame login; // 定义一个窗体对象
    private JComboBox<String> identityComboBox; // 身份选择框对象

    public LoginListener(javax.swing.JFrame login, javax.swing.JTextField jt, javax.swing.JPasswordField jp, JComboBox<String> identityComboBox) {
        this.login = login; // 获取登录界面
        this.jt = jt; // 获取登录界面中的账号输入框对象
        this.jp = jp; // 获取登录界面中的密码输入框对象
        this.identityComboBox = identityComboBox; // 获取身份选择框对象
    }

    public void actionPerformed(ActionEvent e) {
        try {
            Socket socket = ClientDemo.socket; // 获取全局Socket对象



            String loginUsername = jt.getText();
            String loginPassword = new String(jp.getPassword());
            String loginIdentity = (String) identityComboBox.getSelectedItem();
            ClientDemo.username = loginUsername;
            ClientDemo.password = loginPassword;
            ClientDemo.identity = loginIdentity;
            // 发送账号和密码给服务器

            OutputStream outputStream = socket.getOutputStream();
            String message = "LOGIN_IN"+","+loginUsername + "," + loginPassword + "," + loginIdentity;
            outputStream.write((message + "\n").getBytes()); // 确保消息以换行符结尾
            outputStream.flush();

            // 读取服务器的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println("服务器响应: " + response);

            // 根据服务器的响应进行处理
            if ("登录成功".equals(response)) {
                SwingUtilities.invokeLater(() -> new HomePage());

                // 登录成功，跳转到主界面
                login.dispose();
            } else {
                // 处理登录失败的情况
                System.out.println("登录失败");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
