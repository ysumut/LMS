/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Admin;
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
    
    public List<Student> getStudents() {
        return this.model.getStudents();
    }
    
    public void saveStudent() {
        String ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String password = "";
        for(int i = 0; i < 8; i++) password += ch.charAt(random.nextInt(ch.length() + 1));
        
        boolean response = this.model.saveStudent(this.student, password);
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
}
