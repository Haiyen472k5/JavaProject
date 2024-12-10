package project.libraryassistant;

public class IssuedBook {
    private String issueID;
    private String studentID;
    private String book_name;
    private String issueDate;
    private String dueDate;
    private String status;

    IssuedBook(String issueID, String studentID, String book_name, String issueDate, String dueDate, String status) {
        this.issueID = issueID;
        this.studentID = studentID;
        this.book_name = book_name;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
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
}
