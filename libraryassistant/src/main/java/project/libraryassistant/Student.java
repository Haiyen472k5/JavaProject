package project.libraryassistant;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private StringProperty id;
    private StringProperty name;
    private StringProperty university;
    private StringProperty faculty;

    public Student(String id, String name, String university, String faculty) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.university = new SimpleStringProperty(university);
        this.faculty = new SimpleStringProperty(faculty);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getUniversity() {
        return university.get();
    }

    public void setUniversity(String university) {
        this.university.set(university);
    }

    public String getFaculty() {
        return faculty.get();
    }

    public void setFaculty(String faculty) {
        this.faculty.set(faculty);
    }
}
