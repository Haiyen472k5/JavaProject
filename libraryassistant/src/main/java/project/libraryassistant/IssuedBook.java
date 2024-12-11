package project.libraryassistant;

public class IssuedBook {
    private String issueID;
    private String studentID;
    private String book_name;
    private String issueDate;
    private String dueDate;
    private String status;
    private String image;
    private String bookID;

    IssuedBook(String issueID, String studentID, String book_name, String issueDate, String dueDate, String status, String image, String bookID) {
        this.issueID = issueID;
        this.studentID = studentID;
        this.book_name = book_name;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
        this.image = image;
        this.bookID = bookID;
    }

    public String getIssuedID() {
        return issueID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getBookName() {
        return book_name;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public String getBookID() {
        return bookID;
    }
}
