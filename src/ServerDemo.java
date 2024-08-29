import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo {
    private int port = 9999;
    private ServerSocket serverSocket;

    public ServerDemo() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("服务端已启动");
    }

    public PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketoutput = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(socketoutput, true);
        return printWriter;
    }

    public BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketinput = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socketinput));
        return bufferedReader;
    }

    public void server() {
        while (true) {
            Socket socket = null;

            try {
                socket = serverSocket.accept(); // 等待客户连接
                System.out.println("已有客户端连接，地址：" + socket.getInetAddress() + " 端口号：" + socket.getPort());

                PrintWriter writer = this.getWriter(socket);
                BufferedReader reader = this.getReader(socket);
                String msg = null;
                while ((msg = reader.readLine()) != null) {
                    System.out.println(socket.getInetAddress() + " " + socket.getPort() + " 发来的消息：" + msg);
                    String[] parts = msg.split(",");
                    String username = "";
                    String studentname = "";
                    String password = "";
                    String identity = "";
                    String age = "";
                    String email = "";

                    if (parts.length == 3) {
                        username = parts[0];
                        password = parts[1];
                        identity = parts[2];

                        if (InsertDataToMySQL.verifyPassword(username, password, identity)) {
                            writer.println("登录成功");
                        } else {
                            writer.println("登录失败，用户名或密码错误");
                        }
                    }

                    if (parts.length ==6 ) {
                        username = parts[0];
                        studentname = parts[1];
                        password = parts[2];
                        identity = parts[3];
                        age = parts[4];
                        email = parts[5];

                        int resultage = Integer.parseInt(age);
                        if (InsertDataToMySQL.insertUser(username,studentname, password, email, resultage, identity,null)) {
                            writer.println("注册成功");
                        } else {
                            writer.println("注册失败，用户名已存在");
                        }
                    } else {
                        writer.println("格式错误，请按照格式");
                        continue;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            finally {
                try {
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ServerDemo().server();
    }
}
