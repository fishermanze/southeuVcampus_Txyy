import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Personinfo {

    private JPanel personInfoPanel;
    private JTextArea name;
    private JTextArea age;
    private JTextArea username;
    private JTextArea email;
    private String photoPath = "src/cbs.jpg"; // 默认图片路径
    private JLabel userphoto; // 存储用户照片的 JLabel

    public Personinfo() {
        initUI();
        loadUserInfo();
    }

    private void initUI() {
        personInfoPanel = new JPanel();
        personInfoPanel.setLayout(null);

        JLabel titleLabel = new JLabel("个人信息");
        titleLabel.setBounds(180, 1, 280, 30);
        personInfoPanel.add(titleLabel);

        // 初始化用户照片
        userphoto = new JLabel();
        userphoto.setBounds(170, 50, 85, 120);  // 使用固定大小的区域
        updatePhoto(photoPath); // 设置默认照片
        personInfoPanel.add(userphoto);

        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setBounds(50, 190, 60, 30);
        personInfoPanel.add(nameLabel);

        name = new JTextArea();
        name.setBounds(110, 190, 200, 30);
        name.setEditable(false); // 设置为不可编辑
        personInfoPanel.add(name);

        JLabel ageLabel = new JLabel("年龄:");
        ageLabel.setBounds(50, 230, 60, 30);
        personInfoPanel.add(ageLabel);

        age = new JTextArea();
        age.setBounds(110, 230, 200, 30);
        age.setEditable(false); // 设置为不可编辑
        personInfoPanel.add(age);

        JLabel usernameLabel = new JLabel("一卡通号:");
        usernameLabel.setBounds(50, 270, 60, 30);
        personInfoPanel.add(usernameLabel);

        username = new JTextArea();
        username.setBounds(110, 270, 200, 30);
        username.setEditable(false); // 设置为不可编辑
        personInfoPanel.add(username);

        JLabel emailLabel = new JLabel("邮箱:");
        emailLabel.setBounds(50, 310, 60, 30);
        personInfoPanel.add(emailLabel);

        email = new JTextArea();
        email.setBounds(110, 310, 200, 30);
        email.setEditable(false); // 设置为不可编辑
        personInfoPanel.add(email);

        JButton photoButton = new JButton("修改照片");
        photoButton.setBounds(80, 350, 100, 30);
        photoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPhotoPath = choosePhoto();
                if (newPhotoPath != null) {
                    photoPath = newPhotoPath; // 更新 photoPath
                    updatePhoto(photoPath);
                    sendPhotoToServer(photoPath); // 将新的照片发送到服务器
                }
            }
        });
        personInfoPanel.add(photoButton);

        JButton saveButton = new JButton("保存修改");
        saveButton.setBounds(230, 350, 100, 30);
        saveButton.addActionListener(e -> System.out.println("保存个人信息"));
        personInfoPanel.add(saveButton);
    }

    private void updatePhoto(String photoPath) {
        File file = new File(photoPath);
        if (!file.exists()) {
            System.err.println("File does not exist: " + photoPath);
            return;
        }
        try {
            System.out.println("Loading image: " + photoPath);
            Image originalImage = ImageIO.read(file);  // 使用 ImageIO 读取文件
            if (originalImage == null) {
                System.err.println("File is not a recognized image format: " + photoPath);
                return;
            }
            Image scaledImage = originalImage.getScaledInstance(85, 120, Image.SCALE_SMOOTH);
            userphoto.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + e.getMessage());
            // 记录日志或采取其他恢复措施
        }
    }



    private String choosePhoto() {
        // 使用 JFileChooser 让用户选择新照片
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择新的照片");

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath(); // 返回用户选择的文件路径
        }
        return null; // 如果未选择文件则返回null
    }

    private void sendPhotoToServer(String photoPath) {
        try {
            Socket socket = ClientDemo.socket; // 获取全局Socket对象

            // 发送请求到服务器
            OutputStream outputStream = socket.getOutputStream();
            String message = "UPLOAD_PHOTO," + ClientDemo.username + "," + ClientDemo.password + "," + ClientDemo.identity;
            outputStream.write((message + "\n").getBytes()); // 确保消息以换行符结尾
            outputStream.flush();

            // 读取服务器的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println("服务器响应: " + response);

            if ("准备接收".equals(response)) {
                // 读取图片文件并发送
                File file = new File(photoPath);
                long fileSize = file.length();
                outputStream.write((fileSize + "\n").getBytes()); // 发送文件大小并以换行符结尾
                outputStream.flush();

                try (FileInputStream fileInputStream = new FileInputStream(file);
                     BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                    byte[] buffer = new byte[2048];
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("照片上传失败");
                    return;
                }

                // 读取服务器的响应
                response = reader.readLine();
                System.out.println("服务器响应: " + response);

                if ("上传成功".equals(response)) {
                    System.out.println("照片已成功上传到服务器");
                } else {
                    System.out.println("照片上传失败");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    private void sendPhotoToServer(String photoPath) {
//        try {
//            Socket socket = ClientDemo.socket; // 获取全局Socket对象
//
//            // 发送请求到服务器
//            OutputStream outputStream = socket.getOutputStream();
//            String message = "UPLOAD_PHOTO," + ClientDemo.username + "," + ClientDemo.password + "," + ClientDemo.identity;
//            outputStream.write((message + "\n").getBytes()); // 确保消息以换行符结尾
//            outputStream.flush();
//
//            // 读取服务器的响应
//            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String response = reader.readLine();
//            System.out.println("服务器响应: " + response);
//
//            if ("准备接收".equals(response)) {
//                // 读取文件并发送
//                File file = new File(photoPath);
//                String fileExtension = getFileExtension(file); // 获取文件扩展名
//
//                // 发送文件扩展名长度和扩展名
//                byte[] extensionBytes = fileExtension.getBytes();
//                OutputStream os = socket.getOutputStream();
//                os.write(extensionBytes.length); // 发送扩展名长度
//                os.write(extensionBytes); // 发送扩展名
//
//                // 发送文件内容
//                try (FileInputStream fileInputStream = new FileInputStream(file);
//                     BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
//
//                    byte[] buffer = new byte[2048];
//                    int bytesRead;
//                    while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
//                        os.write(buffer, 0, bytesRead);
//                    }
//                    os.flush();
//                }
//
//                // 读取服务器的响应
//                response = reader.readLine();
//                System.out.println("服务器响应: " + response);
//
//                if ("上传成功".equals(response)) {
//                    System.out.println("照片已成功上传到服务器");
//                } else {
//                    System.out.println("照片上传失败");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    public JPanel getPersonInfoPanel() {
        return personInfoPanel;
    }

    private void loadUserInfo() {
        try {
            Socket socket = ClientDemo.socket; // 获取全局Socket对象

            // 发送请求到服务器
            OutputStream outputStream = socket.getOutputStream();
            String message = "GET_USER_INFO," + ClientDemo.username + "," + ClientDemo.password + "," + ClientDemo.identity;
            outputStream.write((message + "\n").getBytes()); // 确保消息以换行符结尾
            outputStream.flush();

            // 读取服务器的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println("服务器响应: " + response);

            // 解析响应并填充到文本区域
            String[] parts = response.split(",");
            if (parts.length == 5) {
                name.setText(parts[0]);
                age.setText(parts[1]);
                username.setText(parts[2]);
                email.setText(parts[3]);
                String photoPath = parts[4];
                updatePhoto(photoPath); // 更新头像
            } else {
                System.err.println("Invalid response format: " + response);
                // 提示用户或采取其他措施
            }
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
            // 提示用户网络连接出现问题
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            // 提示用户程序出现未知错误
        }
    }
}
