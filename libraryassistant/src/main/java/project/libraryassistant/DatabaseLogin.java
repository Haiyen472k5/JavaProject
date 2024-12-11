package project.libraryassistant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseLogin {
    private static final String URL = "jdbc:mysql://localhost:3307/login"; // Thay bằng URL của bạn
    private static final String USER = "root"; // Tên người dùng MySQ
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, "");
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
        }
        return connection;
    }

    public ObservableList<Login> loadLogins() {
        ObservableList<Login> logins = FXCollections.observableArrayList();
        String sql = "SELECT * FROM admin";
        try (Connection connect = getConnection(); Statement stmt = connect.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                logins.add(new Login(
                        rs.getString("account"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getString("image")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logins;
    }

    public void saveLogins(ObservableList<Login> logins) {
        String sql = "INSERT INTO admin(account, password, username, image) VALUES(?,?,?,?)";
        try (Connection connect = getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql)) {
            for (Login login : logins) {
                pstmt.setString(1, login.getAccount());
                pstmt.setString(2, login.getPassword());
                pstmt.setString(3, login.getUsername());
                pstmt.setString(4, "");
                pstmt.executeUpdate();
                System.out.println("Success Student");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getConnection();
    }
}