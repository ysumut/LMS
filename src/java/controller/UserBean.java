/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.User;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import model.UserModel;

/**
 *
 * @author yahya
 */
@Named(value = "UserBean")
@SessionScoped
public class UserBean implements Serializable {

    /**
     * Creates a new instance of PersonBean
     */
    private UserModel model;
    public UserBean() {
    }
    private int id;
    private String email;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public UserModel getModel() {
        if(this.model==null){
            this.model=new UserModel();
        }
        return model;
    }

    public void setModel(UserModel model) {
        this.model = model;
    }
    public void create(){
        User u = new User(this.email,this.password,this.id);
        this.getModel().insert(u);
    }
    public String login(){
        User u = new User(this.email,this.password,this.id);
        String response = this.getModel().login(u);
        
        if(!response.equals("false")) {
            return "student/dashboard?faces-redirect=true";
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Email veya şifre yanlış!");
            return "login?faces-redirect=true";
        }
    }
    public String getList(){
        List<User> userList = this.getModel().getList();
        for(User u: userList) {
            System.out.println(u.getEmail());
        }
        
        return "login";
    }
}
