package controller;

import entity.Student;
import entity.Message;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.MessageModel;

@ManagedBean(name = "MessageBean")
@SessionScoped
public class MessageBean implements Serializable {
    private Student student;
    private MessageModel model = new MessageModel();
    
    public MessageBean() {
        this.student = (Student) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public void setUserSession() {
        this.student = (Student) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public List<List<String>> getMessages() {
        return this.model.getStudentMessages(this.student.getUserId());
    }
}
