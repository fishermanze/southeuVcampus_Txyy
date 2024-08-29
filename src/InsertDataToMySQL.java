import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;

import static java.sql.DriverManager.println;

public class InsertDataToMySQL {

    // 封装的插入函数
    public static boolean insertUser(String username, String name, String password, String email, int age, String identity,String photoPath) {
        Connection conn = null;
        String tableName = "";
        if ("学生".equals(identity)) {
            tableName = "stuid";
        }
        if ("管理员".equals(identity)) {
            tableName = "Adminid";
        }
        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 数据库连接URL、用户名和密码
            String url = "jdbc:mysql://localhost:3306/database_name";
            String usernameDB = "root";
            String passwordDB = "114514";

            // 获取数据库连接
            conn = DriverManager.getConnection(url, usernameDB, passwordDB);

            // 定义SQL插入语句
            String sql = "INSERT INTO " + tableName + " (username, stuname, password, email, age, userphoto) VALUES (?, ?, ?, ?, ?,?)";

            // 创建PreparedStatement对象
            PreparedStatement statement = conn.prepareStatement(sql);

            // 设置SQL语句中的参数
            statement.setString(1, username);
            statement.setString(2, name);
            statement.setString(3, password);
            statement.setString(4, email);
            statement.setInt(5, age);
            statement.setString(6, photoPath);

            // 执行SQL插入语句
            int rowsAffected = statement.executeUpdate();

            // 关闭PreparedStatement
            statement.close();

            // 输出日志
            if (rowsAffected > 0) {
                System.out.println("用户插入成功");
                return true;
            } else {
                System.out.println("用户插入失败");
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 确保连接关闭
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 封装的删除函数
    public static void deleteUserByName(String username, String password, String identity) {
        Connection conn = null;
        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 数据库连接URL、用户名和密码
            String url = "jdbc:mysql://localhost:3306/database_name";
            String usernameDB = "root";
            String passwordDB = "114514";

            // 获取数据库连接
            conn = DriverManager.getConnection(url, usernameDB, passwordDB);

            // 验证用户名和密码
            if (verifyPassword(username, password, identity)) {
                // 定义SQL删除语句
                String sql = "DELETE FROM identity WHERE username = ?";

                // 创建PreparedStatement对象
                PreparedStatement statement = conn.prepareStatement(sql);

                // 设置SQL语句中的参数
                statement.setString(1, username);

                // 执行SQL删除语句
                statement.executeUpdate();

                // 关闭PreparedStatement
                statement.close();
            } else {
                System.out.println("用户名或密码不正确，无法删除用户。");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 确保连接关闭
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 封装的验证密码函数
    public static boolean verifyPassword(String username, String inputPassword, String identity) {
        Connection conn = null;
        String tableName = "";
        if ("学生".equals(identity)) {
            tableName = "stuid";
        }
        if ("管理员".equals(identity)) {
            tableName = "Adminid";
        }

        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 数据库连接URL、用户名和密码
            String url = "jdbc:mysql://localhost:3306/database_name";
            String dbUsername = "root";
            String dbPassword = "114514";

            // 获取数据库连接
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // 定义SQL查询语句
            String sql = "SELECT password FROM " + tableName + " WHERE username = ?";

            // 创建PreparedStatement对象
            PreparedStatement statement = conn.prepareStatement(sql);

            // 设置SQL语句中的参数
            statement.setString(1, username);

            // 执行SQL查询语句
            ResultSet rs = statement.executeQuery();

            // 如果找到匹配的记录
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(inputPassword);
            }

            // 关闭ResultSet、PreparedStatement和Connection
            rs.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 确保连接关闭
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean updatauserphoto(String username, String password, String photoPath, String identity) {
        Connection conn = null;
        String tableName = "";
        if ("学生".equals(identity)) {
            tableName = "stuid";
        }
        if ("管理员".equals(identity)) {
            tableName = "Adminid";
        }
        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 数据库连接URL、用户名和密码
            String url = "jdbc:mysql://localhost:3306/database_name";
            String usernameDB = "root";
            String passwordDB = "114514";

            // 获取数据库连接
            conn = DriverManager.getConnection(url, usernameDB, passwordDB);

            // 验证用户名和密码
            if (verifyPassword(username, password, identity)) {
                // 定义SQL更新语句
                String sql = "UPDATE " + tableName + " SET userphoto = ? WHERE username = ?";

                // 创建PreparedStatement对象
                PreparedStatement statement = conn.prepareStatement(sql);

                // 设置SQL语句中的参数
                statement.setString(1, photoPath);
                statement.setString(2, username);

                // 执行SQL更新语句
                int rowsAffected = statement.executeUpdate();

                // 关闭PreparedStatement
                statement.close();

                // 输出日志
                if (rowsAffected > 0) {
                    System.out.println("用户照片更新成功");
                    return true;
                } else {
                    System.out.println("用户照片更新失败");
                    return false;
                }
            } else {
                System.out.println("用户名或密码不正确，无法更新用户照片。");
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 确保连接关闭
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getUserInfo(String username, String identity) {
        String userInfo = "";
        String tableName = "";

        Connection conn = null;
        if ("学生".equals(identity)) {
            tableName = "stuid";
        }
        if ("管理员".equals(identity)) {
            tableName = "Adminid";
        }
        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 数据库连接URL、用户名和密码
            String url = "jdbc:mysql://localhost:3306/database_name";
            String usernameDB = "root";
            String passwordDB = "114514";

            // 获取数据库连接
            conn = DriverManager.getConnection(url, usernameDB, passwordDB);

            // 定义SQL查询语句
            String sql = "SELECT stuname, age, username, email, userphoto FROM " + tableName + " WHERE username = ?";

            // 创建PreparedStatement对象
            PreparedStatement statement = conn.prepareStatement(sql);

            // 设置SQL语句中的参数
            statement.setString(1, username);

            // 执行SQL查询语句
            ResultSet rs = statement.executeQuery();

            // 如果找到匹配的记录
            if (rs.next()) {
                String name = rs.getString("stuname");
                int age = rs.getInt("age");
                String user = rs.getString("username");
                String email = rs.getString("email");
                String userphoto = rs.getString("userphoto");
                userInfo = name + "," + age + "," + user + "," + email + "," + userphoto;
            }

            // 关闭ResultSet、PreparedStatement和Connection
            rs.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 确保连接关闭
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userInfo;
    }



    public static void main(String[] args) {
        // 调用封装的插入函数
        // InsertDataToMySQL.insertUser("g367o", "John Doe", "12311", "syz22eki@example.com", 30, "学生");
        // InsertDataToMySQL.insertUser("wdwh", "Jane Doe", "1111", "sy71ki@example.com", 30, "管理员");
        // InsertDataToMySQL.insertUser("gede", "Alice", "1e1", "syedeample.com", 30, "管理员");
        // InsertDataToMySQL.insertUser("donald", "Donald", "1234", "defgf@example.com", 25, "学生");

        String userInfo = InsertDataToMySQL.getUserInfo("suzuki","学生");
        System.out.println(userInfo);

        updatauserphoto("suzuki", "xyy588", "photo.jpg", "学生");
        // 调用封装的删除函数
        // InsertDataToMySQL.deleteUserByName("john_doe", "password123", "学生");

        // 调用封装的验证密码函数
        // boolean isValid = verifyPassword("jane_doe", "password123", "管理员");
        // System.out.println("密码验证结果: " + isValid);
    }
}
