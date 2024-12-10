package project.libraryassistant;

public class IssuedBook {
    private String issued_id;
    private String student_name;
    private String book_name;
    private String issue_date;
    private String due_date;
    private String status;

    public String getIssued_id() {
        return issued_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public String getDue_date() {
        return due_date;
    }
}
