package project.libraryassistant;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
public class ReturnBookHandle {
    private static final String URL = "jdbc:mysql://localhost:3307/managebook"; // Thay bằng URL của bạn
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
    // Hàm tính tiền phạt trả trễ
    public int calculateLatePenalty(String bookId, String studentId, LocalDate returnDate) throws SQLException {
        String sql = "SELECT dueDate FROM issuedbook WHERE bookID = ? AND studentID = ?";
        try (Connection connect = getConnection(); PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, bookId);
            statement.setString(2, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LocalDate dueDate = resultSet.getDate("dueDate").toLocalDate();
                long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                if (daysLate <= 0) {
                    return 0; // Không có tiền phạt nếu trả đúng hạn
                }
                // Truy vấn bảng returnbooklaterule để tính tiền phạt
                String ruleQuery = "SELECT fine FROM returnbooklaterule WHERE ? BETWEEN SUBSTRING_INDEX(returnlatefor, ',', 1) AND SUBSTRING_INDEX(returnlatefor, ',', -1) OR returnlatefor LIKE '>7 ngày'";
                try (Connection ruleConnect = getConnection(); PreparedStatement ruleStatement = connect.prepareStatement(sql)) {
                    ruleStatement.setLong(1, daysLate);
                    ResultSet ruleResult = ruleStatement.executeQuery();
                    if (ruleResult.next()) {
                        return ruleResult.getInt("fine");
                    }
                }
            }
        }
        return 0; // Mặc định không có phạt nếu không tìm thấy
    }
    // Hàm tính tiền phạt do tình trạng sách
    public int calculateDamagePenalty(int bookCondition) throws SQLException {
        String sql = "SELECT fine FROM returnbookdamagerule WHERE ? BETWEEN bookdamagemin AND bookdamagemax";
        try (Connection connect = getConnection(); PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setInt(1, bookCondition);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("fine");
            }
        }
        return 0; // Mặc định không có phạt nếu không tìm thấy
    }
    // Hàm lưu thông tin trả sách vào bảng returned_books và xóa bản ghi mượn
    public void recordReturnBook(String bookId, String studentId, LocalDate returnDate, int bookCondition) throws SQLException {
        // Tính tiền phạt trả trễ
        int latePenalty = calculateLatePenalty(bookId, studentId, returnDate);
        // Tính tiền phạt do tình trạng sách
        int damagePenalty = calculateDamagePenalty(bookCondition);
        // Tính tổng tiền phạt
        int totalPenalty = latePenalty + damagePenalty;
        // Truy vấn ID rule trễ
        String lateRuleQuery = "SELECT id FROM returnbooklaterule WHERE ? BETWEEN SUBSTRING_INDEX(returnlatefor, ',', 1) AND SUBSTRING_INDEX(returnlatefor, ',', -1) OR returnlatefor LIKE '>7 ngày'";
        int lateRuleId = -1;
        try (Connection connect = getConnection(); PreparedStatement statement = connect.prepareStatement(lateRuleQuery)) {
            statement.setLong(1, ChronoUnit.DAYS.between(LocalDate.now(), returnDate));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lateRuleId = resultSet.getInt("id");
            }
        }
        // Truy vấn ID rule tình trạng sách
        String damageRuleQuery = "SELECT id FROM returnbookdamagerule WHERE ? BETWEEN bookdamagemin AND bookdamagemax";
        int damageRuleId = -1;
        try (Connection connect = getConnection(); PreparedStatement statement = connect.prepareStatement(damageRuleQuery)) {
            statement.setInt(1, bookCondition);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                damageRuleId = resultSet.getInt("id");
            }
        }
        // Xóa bản ghi trong bảng issuedbook
        String deleteQuery = "DELETE FROM issuedbook WHERE bookID = ? AND studentID = ?";
        try (Connection connect = getConnection(); PreparedStatement statement = connect.prepareStatement(deleteQuery)) {
            statement.setString(1, bookId);
            statement.setString(2, studentId);
            statement.executeUpdate();
        }
    }
    public static void main(String[] args) {
        getConnection();
    }
}