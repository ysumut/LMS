package entity;

import java.util.List;

public class Student extends User {
    private int semester, registration_year;
    private List<String> departments;
    private String departments_str;

    public Student() {
        
    }
    
    public Student(int userId, String email, int semester, int registration_year) {
        super(userId, email, "student");
        this.semester = semester;
        this.registration_year = registration_year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getRegistration_year() {
        return registration_year;
    }

    public void setRegistration_year(int registration_year) {
        this.registration_year = registration_year;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
        this.departments_str = String.join(", ", departments);
    }

    public String getDepartments_str() {
        return departments_str;
    }
}
