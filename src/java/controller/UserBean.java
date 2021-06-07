package controller;

import entity.User;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
            return "/views/" + this.user.getType() + "/dashboard.xhtml?faces-redirect=true";
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Email veya şifre yanlış!");
            
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String url = request.getRequestURL().toString();
            return url.substring(url.indexOf("/views"));
        }
    }
    public String logout() {
        User u = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        String user_type = u.getType();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
        
        if(user_type.equals("student") || user_type.equals("lecturer")) 
            return "/views/login?faces-redirect=true";
        else
            return "/views/admin/adminlogin?faces-redirect=true";
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
    
    public void updateUser() {
        boolean response = this.model.updateUser(this.user);

        if (response) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", this.user);
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("success", "Profil güncellendi!");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Bir hata ile karşılaşıldı!");
        }
    }

    public void updatePassword() {
        if (this.user.getNew_pass().length() < 6 || this.user.getNew_pass().length() > 18) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Yeni şifre uzunluğu 6 ile 18 karakter arasında olmalıdır!");
            return;
        }
        if (!this.user.getNew_pass().equals(this.user.getNew_pass_repeat())) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Yeni şifreler eşleşmiyor!");
            return;
        }

        String response = this.model.updatePassword(user);

        if (response.equals("true")) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("success", "Şifre güncellendi!");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", response);
        }
    }
}
