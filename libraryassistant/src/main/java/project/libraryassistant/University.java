package project.libraryassistant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class University {
    private String name;
    private List<String> faculties;

    public University(String name, List<String> faculties) {
        this.name = name;
        this.faculties = faculties;
    }

    public String getName() {
        return name;
    }

    public List<String> getFaculties() {
        return faculties;
    }

    public static List<University> getUniversities() {
        List<University> universities = new ArrayList<>();

        universities.add(new University("UET", Arrays.asList("Faculty of Engineering", "Faculty of Information Technology", "Faculty of Electronics and Telecommunications", "Faculty of Engineering Physics and Nanotechnology", "Faculty of Mechatronics and Automation")));
        universities.add(new University("ULIS", Arrays.asList("Faculty of English Language Teacher Education", "Faculty of French Language and Culture", "Faculty of Japanese Language and Culture", "Faculty of Korean Language and Culture", "Faculty of Linguistics and Culture of English-Speaking Countries")));
        universities.add(new University("UEB", Arrays.asList("Faculty of Business Administration", "Faculty of Economics", "Faculty of Accounting and Auditing", "Faculty of Management Science", "Faculty of Development Economics")));
        universities.add(new University("UEd", Arrays.asList("Faculty of Educational Psychology", "Faculty of Primary Education", "Faculty of Special Education", "Faculty of Curriculum Development", "Faculty of Educational Management")));
        universities.add(new University("VJU", Arrays.asList("Faculty of Environmental Engineering", "Faculty of Advanced Technology", "Faculty of Interdisciplinary Sciences", "Faculty of Business Innovation")));
        universities.add(new University("UL", Arrays.asList("Faculty of Law", "Faculty of International Law", "Faculty of Civil Law", "Faculty of Economic Law", "Faculty of Criminal Law", "Faculty of Administrative Law")));
        universities.add(new University("HUS", Arrays.asList("Faculty of Mathematics", "Faculty of Physics", "Faculty of Chemistry", "Faculty of Biology", "Faculty of Geology", "Faculty of Geography", "Faculty of Environment", "Faculty of Information Technology")));
        universities.add(new University("UMP", Arrays.asList("Faculty of Medicine", "Faculty of Pharmacy", "Faculty of Public Health", "Faculty of Nursing", "Faculty of Traditional Medicine")));
        universities.add(new University("IS", Arrays.asList("Faculty of International Studies", "Faculty of Global Management", "Faculty of Modern Technology", "Faculty of Business Analytics", "Faculty of International Law and Policy")));
        universities.add(new University("SIS", Arrays.asList("Faculty of Software Engineering", "Faculty of System Analysis", "Faculty of Network and Security", "Faculty of Artificial Intelligence", "Faculty of Data Science")));

        return universities;
    }
}
