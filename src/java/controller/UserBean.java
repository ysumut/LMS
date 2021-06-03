/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.User;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import model.UserModel;

/**
 *
 * @author yahya
 */
@Named(value = "UserBean")
@Dependent
public class UserBean {

    /**
     * Creates a new instance of PersonBean
     */
    private UserModel model;
    public UserBean() {
    }
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        User u = new User(this.username,this.password,this.id);
        this.getModel().insert(u);
    }
    public void login(){
        User u = new User(this.username,this.password,this.id);
        this.getModel().login(u);
    }
    public List<User> getList(){
       return this.getModel().getList();
    }
}
