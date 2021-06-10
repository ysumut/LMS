/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Admin;
import entity.Lecturer;
import entity.Student;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.AdminModel;

/**
 *
 * @author yahya
 */
@ManagedBean(name = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

    private final AdminModel model = new AdminModel();
    private final Random random = new Random();
    private Admin admin = new Admin();
    
    private Student student = new Student();
    private Lecturer lecturer = new Lecturer();
    private Admin new_admin = new Admin();

    public AdminBean() {
        this.admin = (Admin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public void setUserSession() {
        this.admin = (Admin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public Admin getAdmin() {
        return admin;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Admin getNew_admin() {
        return new_admin;
    }

    public void setNew_admin(Admin new_admin) {
        this.new_admin = new_admin;
    }
    
    public List<String> getReporting() {
        return this.model.getReporting();
    }
    
    private String generatePassword() {
        String ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String password = "";
        for(int i = 0; i < 8; i++) password += ch.charAt(random.nextInt(ch.length() + 1));
        return password;
    }
    
    public List<Student> getStudents() {
        return this.model.getStudents();
    }
    
    public List<Lecturer> getLecturers() {
        return this.model.getLecturers();
    }
    
    public List<Admin> getAdmins() {
        return this.model.getAdmins();
    }
    
    public void saveStudent() {
        boolean response = this.model.saveStudent(this.student, this.generatePassword());
        if(response) {
            this.student = new Student();
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("success", "Yeni öğrenci eklendi!");
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Bir hata ile karşılaşıldı!");
        }
    }
    
    public void saveLecturer() {
        boolean response = this.model.saveLecturer(this.lecturer, this.generatePassword());
        if(response) {
            this.lecturer = new Lecturer();
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("success", "Yeni öğretim görevlisi eklendi!");
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Bir hata ile karşılaşıldı!");
        }
    }
    
    public void saveAdmin() {
        boolean response = this.model.saveAdmin(this.new_admin, this.generatePassword());
        if(response) {
            this.new_admin = new Admin();
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("success", "Yeni admin eklendi!");
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Bir hata ile karşılaşıldı!");
        }
    }
    
    public void deleteUser(int user_id) {
        boolean response = this.model.removeUser(user_id);
        if(response) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("success", "Kullanıcı silindi!");
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Bir hata ile karşılaşıldı!");
        }
    }
}
