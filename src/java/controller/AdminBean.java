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

/**
 *
 * @author yahya
 */
@ManagedBean(name = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

    private AdminModel model = new AdminModel();
    private Admin admin = new Admin();

    public AdminBean() {
        this.admin = (Admin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public void setUserSession() {
        this.admin = (Admin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public Admin getAdmin() {
        return admin;
    }
}
