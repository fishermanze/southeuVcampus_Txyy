import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;

public class ClientDemo {
    public static Socket socket; // 全局Socket对象
    private static String host = "frp-fun.top"; // 服务器地址
    private static int port = 29635; // 服务器端口
    public static String username ;
    public static String password ;
    public static String identity ;

    public ClientDemo() {
        try {
            socket = new Socket(host, port); // 创建Socket连接到服务器
            System.out.println("连接到服务器");
        } catch (IOException e) {
            e.printStackTrace();
        }


        Login login = new Login();
        login.initUI();
    }

    public static void main(String[] args) {
        new ClientDemo();
    }
}
