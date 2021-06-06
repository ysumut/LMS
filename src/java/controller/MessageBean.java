package controller;

import entity.Message;
import entity.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.MessageModel;

@ManagedBean(name = "MessageBean")
@SessionScoped
public class MessageBean implements Serializable {
    private User user;
    private Message message = new Message();
    private MessageModel model = new MessageModel();
    private int departmentID;
    
    public MessageBean() {
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public void setUserSession() {
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public Message getMessage() {
        return message;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
    
    public List<List<String>> getStudentMessages() {
        return this.model.getStudentMessages(this.user.getUserId());
    }
    
    public List<List<String>> getLecturerMessages() {
        return this.model.getLecturerMessages(this.user.getUserId());
    }
    
    public Map<String, String> getLecturerDepartments() {
        return this.model.getLecturerDepartments(this.user.getUserId());
    }
    
    public void saveMessage() {
        this.message.setFrom_id(user.getUserId());
        this.message.setDepartment_id(this.departmentID);
        this.message.setCreated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        
        boolean response = this.model.saveMessage(this.message);
        this.message = new Message();
        
        if(response) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("success", "Yeni mesaj eklendi!");
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Bir hata ile karşılaşıldı!");
        }
    }
}
