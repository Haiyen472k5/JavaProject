package project.libraryassistant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseIssuedBook {
    private static final String URL = "jdbc:mysql://localhost:3307/managebook"; // Thay bằng URL của bạn
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

    public void saveIssuedBooks(ObservableList<IssuedBook> books) {
        String sql = "INSERT INTO issuedbook (issuedID, studentName, bookName, issueDate, dueDate, status) "
                + "VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                + "issuedID = VALUES(issuedID), studentName = VALUES(studentName), bookName = VALUES(bookName),"
                + "issueDate = VALUES(issueDate), dueDate = VALUES(dueDate), status = VALUES(status)";
        try (Connection connect = getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql)) {
            for (IssuedBook book : books) {
                pstmt.setString(1, book.getIssuedID());
                pstmt.setString(2, book.getStudentID());
                pstmt.setString(3, book.getBookName());
                pstmt.setString(4, book.getIssueDate());
                pstmt.setString(5, book.getDueDate());
                pstmt.setString(6, book.getStatus());
                pstmt.executeUpdate();
                System.out.println("Success");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<IssuedBook> loadIssuedBooks() {
        ObservableList<IssuedBook> books = FXCollections.observableArrayList();
        String sql = "SELECT * FROM issuedbook";
        try (Connection connect = getConnection(); Statement stmt = connect.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new IssuedBook(
                        rs.getString("issuedID"),
                        rs.getString("studentName"),
                        rs.getString("bookName"),
                        rs.getString("issueDate"),
                        rs.getString("dueDate"),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public boolean updateIssuedBookInDatabase(IssuedBook book) {
        String sql = "UPDATE issuedbook SET status = ? WHERE issueID = ?";
        try (Connection connect = getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql)) {
            // Thiết lập các tham số
            pstmt.setString(1, book.getIssuedID());
            pstmt.setString(2, book.getStudentID());
            pstmt.setString(3, book.getBookName());
            pstmt.setString(4, book.getIssueDate());
            pstmt.setString(5, book.getDueDate());
            pstmt.setString(6, book.getStatus());

            // Thực thi truy vấn
            int rowAffected = pstmt.executeUpdate();
            return rowAffected > 0; // Trả về true nếu có bản ghi được cập nhật
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
