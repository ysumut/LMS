package controller;

import entity.Student;
import entity.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.StudentModel;

@ManagedBean(name = "StudentBean")
@SessionScoped
public class StudentBean implements Serializable {
    private Student student;
    private StudentModel model = new StudentModel();
    private String departments = "";
    
    public StudentBean() {
        this.student = (Student) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public Student getStudentInfo() {
        return this.model.getStudentByID(this.student.getUserId());
    }

    public Student getStudent() {
        return student;
    }

    public String getDepartments() {
        return departments;
    }
}
