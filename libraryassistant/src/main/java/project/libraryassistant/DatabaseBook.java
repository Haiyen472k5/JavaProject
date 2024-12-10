package project.libraryassistant;

import java.security.DrbgParameters;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class DatabaseBook {
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

    public void saveBooks(ObservableList<Book> books) {
        String sql = "INSERT INTO book (id, title, author, genre, quantity, image) "
                + "VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                + "title = VALUES(title), author = VALUES(author), genre = VALUES(genre), "
                + "quantity = VALUES(quantity), image = VALUES(image)";
        try (Connection connect = getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql)) {
            for (Book book : books) {
                pstmt.setString(1, book.getId());
                pstmt.setString(2, book.getTitle());
                pstmt.setString(3, book.getAuthor());
                pstmt.setString(4, book.getGenre());
                pstmt.setInt(5, book.getQuantity());
                pstmt.setString(6, book.getCoverImageUrl());
                pstmt.executeUpdate();
                System.out.println("Success");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Book> loadBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String sql = "SELECT * FROM book";
        try (Connection connect = getConnection(); Statement stmt = connect.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getInt("quantity"),
                        rs.getString("image")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public void deleteBookFromDatabase(String bookId) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connect = getConnection()) {
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, bookId);
            pstmt.executeUpdate();
            System.out.println("Delete success.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean updateBookInDatabase(Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, genre = ?, quantity = ?, image = ? WHERE id = ?";
        try (Connection connect = getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql)) {
            // Thiết lập các tham số
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());
            pstmt.setInt(4, book.getQuantity());
            pstmt.setString(5, book.getCoverImageUrl());
            pstmt.setString(6, book.getId());

            // Thực thi truy vấn
           int rowAffected = pstmt.executeUpdate();
            return rowAffected > 0; // Trả về true nếu có bản ghi được cập nhật
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        getConnection();
    }
}