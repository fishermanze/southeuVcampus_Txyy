import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servermulithread {
    private int port = 9999;
    private ServerSocket serverSocket;

    public Servermulithread() throws IOException {
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
            try {
                Socket socket = serverSocket.accept(); // 等待客户连接
                System.out.println("已有客户端连接，地址：" + socket.getInetAddress() + " 端口号：" + socket.getPort());

                // 为每个客户端连接创建一个新的线程
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Servermulithread().server();
    }

    // 处理客户端连接的线程类
    class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter writer = getWriter(socket);
                BufferedReader reader = getReader(socket);
                String msg = null;
                while ((msg = reader.readLine()) != null) {
                    System.out.println(socket.getInetAddress() + " " + socket.getPort() + " 发来的消息：" + msg);
                    String[] parts = msg.split(",");
                    String command = parts[0];

                    if ("GET_USER_INFO".equals(command) && parts.length == 4) {
                        String username = parts[1];
                        String password = parts[2];
                        String identity = parts[3];

                        if (InsertDataToMySQL.verifyPassword(username, password, identity)) {
                            String userInfo = InsertDataToMySQL.getUserInfo(username, identity);
                            writer.println(userInfo);
                        } else {
                            writer.println("登录失败，用户名或密码错误");
                        }
                    } else if ("UPLOAD_PHOTO".equals(command) && parts.length == 4) {
                        String username = parts[1];
                        String password = parts[2];
                        String identity = parts[3];
                        if (InsertDataToMySQL.verifyPassword(username, password, identity)) {
                            writer.println("准备接收");

                            // 指定保存目录（例如：images 文件夹）
                            String directoryPath = "images";
                            // 创建保存目录（如果目录不存在）
                            File directory = new File(directoryPath);
                            if (!directory.exists()) {
                                directory.mkdirs(); // 创建目录
                            }

                            // 使用当前时间戳作为文件名，并指定保存目录
                            String filename = directoryPath + File.separator + System.currentTimeMillis() + ".jpg";

                            // 接收图片文件并保存到指定路径
                            try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
                                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

                                BufferedReader fileSizeReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                String fileSizeStr = fileSizeReader.readLine();
                                long fileSize = Long.parseLong(fileSizeStr);

                                byte[] buffer = new byte[16384];
                                int bytesRead;
                                long totalBytesRead = 0;
                                while (totalBytesRead < fileSize && (bytesRead = socket.getInputStream().read(buffer)) != -1) {
                                    bufferedOutputStream.write(buffer, 0, bytesRead);
                                    totalBytesRead += bytesRead;
                                }
                                bufferedOutputStream.flush();

                                System.out.println("文件接收完成，保存路径: " + filename);

                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("文件接收出错: " + e.getMessage());
                                writer.println("上传失败，文件接收出错");
                                return; // 如果接收文件失败，直接返回
                            }

                            writer.println("上传成功");
                            System.out.println("照片已成功上传到服务器");
                        } else {
                            writer.println("上传失败，用户名或密码错误");
                        }
                    } else if ("LOGIN_IN".equals(command) && parts.length == 4) {
                        String username = parts[1];
                        String password = parts[2];
                        String identity = parts[3];

                        if (InsertDataToMySQL.verifyPassword(username, password, identity)) {
                            writer.println("登录成功");
                        } else {
                            writer.println("登录失败，用户名或密码错误");
                        }
                    } else if ("REGISTER".equals(command) && parts.length == 7) {
                        String username = parts[1];
                        String studentname = parts[2];
                        String password = parts[3];
                        String identity = parts[4];
                        String age = parts[5];
                        String email = parts[6];

                        int resultage = Integer.parseInt(age);
                        if (InsertDataToMySQL.insertUser(username, studentname, password, email, resultage, identity, null)) {
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
            } finally {
                try {
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
