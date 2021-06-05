package controller;

import entity.User;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UserModel;


@ManagedBean(name = "UserBean")
@SessionScoped
public class UserBean {
    public UserBean() {
    }
    
    private User user;
    private UserModel model;
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
        //User u = new User(this.email,this.password,this.id);
        //this.getModel().insert(u);
    }
    public String login(){
        this.user = this.getModel().login(this.email, this.password);
        
        if(this.user.getStatus()) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", this.user);
            return "student/dashboard?faces-redirect=true";
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Email veya şifre yanlış!");
            return "login?faces-redirect=true";
        }
    }
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
        return "/views/login?faces-redirect=true";
    }
    public String getList(){
        List<User> userList = this.getModel().getList();
        for(User u: userList) {
            System.out.println(u.getEmail());
        }
        
        return "login";
    }
    
    public User getUser() {
        return user;
    }
}
