/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Admin;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.AdminModel;
import model.DBConnection;

/**
 *
 * @author yahya
 */
@ManagedBean(name = "adminBean")
@SessionScoped
public class AdminBean implements Serializable{

    /**
     * Creates a new instance of AdminBean
     */
    public AdminBean() {
    }
    private AdminModel adminmodel;
    private String username;
    private String password;

    public AdminModel getAdminmodel() {
        if (this.adminmodel == null) {
            this.adminmodel = new AdminModel();
        }
        return adminmodel;
    }

    public void setAdminmodel(AdminModel adminmodel) {
        this.adminmodel = adminmodel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        Admin a = new Admin(this.username, this.password);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        boolean response = this.adminmodel.login(a);
        
        if (response) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("durum", "durum başarılı");
            return "secret/adminpage?faces-redirect=true";
        }else{
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("durum", "hata");
            return "loginadmin";
        }

    }
}