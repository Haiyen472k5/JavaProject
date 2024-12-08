package project.libraryassistant;

public class Student {
    private String id;
    private String name;
    private String university;
    private String faculty;

    public Student(String id, String name, String university, String faculty) {
        this.id = id;
        this.name = name;
        this.university = university;
        this.faculty = faculty;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
