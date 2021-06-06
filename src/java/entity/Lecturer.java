package entity;

import java.util.List;

public class Lecturer extends User {
    private int registration_year;
    private List<String> departments;
    private String departments_str;

    public Lecturer() {
        
    }

    public Lecturer(int userId, String full_name, String email, int registration_year) {
        super(userId, full_name, email, "lecturer");
        this.registration_year = registration_year;
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
