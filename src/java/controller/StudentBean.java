package controller;

import entity.Student;
import entity.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.StudentModel;

@ManagedBean(name = "StudentBean")
@SessionScoped
public class StudentBean implements Serializable {
    private Student student;
    private StudentModel model = new StudentModel();
    
    public StudentBean() {
        this.student = (Student) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public void setUserSession() {
        this.student = (Student) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public Student getStudent() {
        return student;
    }

    public Student getStudentInfo() {
        return this.model.getStudentByID(this.student.getUserId());
    }
    
    public List<List<String>> getLessonsNote() {
        return this.model.getLessonNotes(this.student.getUserId());
    }
    
    public List<List<String>> getLessons() {
        return this.model.getLessons(this.student.getUserId());
    }
}
