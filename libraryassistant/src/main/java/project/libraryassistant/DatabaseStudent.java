package project.libraryassistant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseStudent {
    private static final String URL = "jdbc:mysql://localhost:3307/student"; // Thay bằng URL của bạn
    private static final String USER = "root"; // Tên người dùng MySQ
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, "1234");
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
        }
        return connection;
    }

    public void saveStudents(ObservableList<Student> students) {
        String sql = "INSERT INTO students (student_id, name, university, faculty) "
                + "VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "student_id = VALUES(student_id), name = VALUES(name), university = VALUES(university), faculty = VALUES(faculty)";

        try (Connection connect = getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql)) {
            for (Student student : students) {
                pstmt.setString(1, student.getId());
                pstmt.setString(2, student.getName());
                pstmt.setString(3, student.getUniversity());
                pstmt.setString(4, student.getFaculty());
                pstmt.executeUpdate();
                System.out.println("Success");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Student> loadStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String sql = "SELECT * FROM students";
        try (Connection connect = getConnection(); Statement stmt = connect.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("university"),
                        rs.getString("faculty")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public void deleteStudentFromDatabase(String bookId) {
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

    public static void main(String[] args) {
        getConnection();
    }
}
